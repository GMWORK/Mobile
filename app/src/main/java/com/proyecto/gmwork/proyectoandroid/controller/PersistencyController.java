package com.proyecto.gmwork.proyectoandroid.controller;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.j256.ormlite.dao.ForeignCollection;
import com.proyecto.gmwork.proyectoandroid.Model.Categoria;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.ClienteLog;
import com.proyecto.gmwork.proyectoandroid.Model.Horas;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoProducto;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.Model.mapping.CategoriaVista;
import com.proyecto.gmwork.proyectoandroid.Model.mapping.ClienteVista;
import com.proyecto.gmwork.proyectoandroid.Model.mapping.PedidoVista;
import com.proyecto.gmwork.proyectoandroid.Model.mapping.ProductoVista;
import com.proyecto.gmwork.proyectoandroid.Model.mapping.UsuarioVista;
import com.proyecto.gmwork.proyectoandroid.controller.dao.CategoriaDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.dao.ClienteDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.dao.HoraDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.dao.PedidoDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.dao.PedidoProductoDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.dao.ProductoDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.dao.UsuarioDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.utilidades.MontarSubida;
import com.proyecto.gmwork.proyectoandroid.controller.utilidades.ThreadActualizarDownloadLogs;
import com.proyecto.gmwork.proyectoandroid.controller.utilidades.ThreadActualizarUpload;

import org.joda.time.DateTime;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by mateo on 30/04/15.
 */
public class PersistencyController {
    private PersistencyWebController perWeb;
    private ClienteDAOController cliDAO;
    private ProductoDAOController proDAO;
    private PedidoDAOController peDAO;
    private UsuarioDAOController usuDAO;
    private CategoriaDAOController catDAO;
    private PedidoProductoDAOController pepoDAO;
    private HoraDAOController hoDAO;
    private OpenLiteHelper bd;
    private Context con;


    public PersistencyController(Context context) throws SQLException {
        con = context;
        bd = new OpenLiteHelper(con);
        cliDAO = new ClienteDAOController(context);
        peDAO = new PedidoDAOController(context);
        proDAO = new ProductoDAOController(context);
        usuDAO = new UsuarioDAOController(context);
        catDAO = new CategoriaDAOController(context);
        pepoDAO = new PedidoProductoDAOController(context);
        perWeb = new PersistencyWebController(con, this);
        hoDAO = new HoraDAOController(con);

    }

    public void actualizarDatosLocalesActivar() throws InterruptedException, SQLException {
        String[] aDescargar = new String[]{"categoriasadescargar", "productosadescargar", "usuarioadescargar", "clienteadescargar", "pedidosadescargar", "pedidoproductosadescargar", "date"};
        new ThreadActualizarDownloadLogs(aDescargar, this, con).execute(aDescargar);
    }

    public void guardarDatosBajadosActivar() throws SQLException {
        if (RequiredSOS() == 0) {
            perWeb.comprovarSOS(isNetworkAvailable());
        }
    }

    public boolean hacerLogin(String username, String password) throws SQLException {
        Usuario user = new Usuario();
        user.setUsername(username);
        user.setPassword(password);

        // cliDAO.addCliente(a);
      /* if(cliDAO.filtrarCliente(a) != null){
            return true;
        }else{
            return false;
        }*/
        if (usuDAO.hacerLogin(user)) {
            return true;
        } else {
            return false;
        }

    }

    public void subirDatosLocales() throws SQLException {
        TreeMap<String, List<String[]>> map = new TreeMap<String, List<String[]>>();

        map.put("cliente", MontarSubida.montarCliente(cliDAO,getUltimaSubida()));
        map.put("pedido",MontarSubida.montarPedido(peDAO,getUltimaSubida()));
        new  ThreadActualizarUpload(map,con).execute();



    }

    public void actualizarDatosLocales(TreeMap<String, ArrayList> map) throws SQLException {
        Categoria cat = null;
        DateTime ultima = DateTime.parse(getUltimaBajada());
        for (Object obj : map.get("HoraBajada")) {
            Horas hora = (Horas) obj;
            hoDAO.EditarPedido(hora);
        }

        for (Object obj : map.get("Categoria")) {
            CategoriaVista vista = (CategoriaVista) obj;
            if (vista.getFecha().compareTo(ultima) > 0) {
                switch (vista.getOp()) {
                    case "I":
                        catDAO.addCategoria(new Categoria(vista.getNombre(), vista.getDescuento()));
                        break;
                    case "U":
                        Categoria catlocal = catDAO.filtrarCategoria(vista.getNombre());
                        catlocal.setNombre(vista.getNombre());
                        catlocal.setDescuento(vista.getDescuento());
                        catDAO.EditarCategoria(catlocal);
                        break;
                    case "D":
                        catDAO.removeCategoria(vista.getNombre());
                        break;
                }
            }
        }
        for (Object obj : map.get("Productos")) {
            ProductoVista vista = (ProductoVista) obj;
            if (vista.getFecha().compareTo(ultima) > 0) {
                switch (vista.getOp()) {
                    case "I":
                        //String nombre, double precio, byte[] img, boolean inhabilitats, double descuento
                        proDAO.addProducto(new Producto(vista.getNombre(), vista.getPrecio(), vista.getImg(), vista.isInhabilitats(), vista.getDescuento()));
                        break;
                    case "U":
                        Producto catlocal = proDAO.filtrarProducto(vista.getNombre());
                        catlocal.setNombre(vista.getNombre());
                        catlocal.setDescuento(vista.getDescuento());
                        catlocal.setImg(vista.getImg());
                        catlocal.setInhabilitats(vista.isInhabilitats());
                        catlocal.setPrecio(vista.getPrecio());
                        proDAO.EditarProducto(catlocal);
                        break;
                    case "D":
                        proDAO.removeProducto(vista.getNombre());
                        break;
                }
            }
        }
        for (Object obj : map.get("Usuario")) {
            UsuarioVista vista = (UsuarioVista) obj;
            if (vista.getFecha().compareTo(ultima) > 0) {
                switch (vista.getOp()) {
                    case "I":
                        //String nif, String nombre, String apellidos, String calle, String poblacion, boolean administrador, String username, String password
                        usuDAO.addUsuario(new Usuario(vista.getNif(), vista.getNombre(), vista.getApellidos(), vista.getCalle(), vista.getPoblacion(), vista.isAdministrador(), vista.getUsername(), vista.getPassword()));
                        break;
                    case "U":
                        Usuario catlocal = usuDAO.filtrarUsuario(vista.getNif());
                        catlocal.setNombre(vista.getNombre());
                        catlocal.setApellidos(vista.getApellidos());
                        catlocal.setCalle(vista.getCalle());
                        catlocal.setPoblacion(vista.getPoblacion());
                        catlocal.setAdministrador(vista.isAdministrador());
                        catlocal.setUsername(vista.getUsername());
                        catlocal.setPassword(vista.getPassword());
                        usuDAO.EditarProducto(catlocal);
                        break;
                    case "D":
                        usuDAO.removeUsuario(vista.getNombre());
                        break;
                }
            }
        }
        for (Object obj : map.get("Cliente")) {
            ClienteVista vista = (ClienteVista) obj;
            if (vista.getFecha().compareTo(ultima) > 0) {
                switch (vista.getOp()) {
                    case "I":
                        //String nif, String nombre, String apellidos, double longitud, double latitud, String calle, String poblacion, String proximaVisita                        usuDAO.addUsuario(new Usuario(vista.getNif(), vista.getNombre(), vista.getApellidos(), vista.getCalle(), vista.getPoblacion(), vista.isAdministrador(), vista.getUsername(), vista.getPassword()));
                        cliDAO.addCliente(new Cliente(vista.getNif(), vista.getNombre(), vista.getApellidos(), vista.getLongitud(), vista.getLatitud(), vista.getCalle(), vista.getPoblacion(), vista.getProximaVisita().toString()));
                        break;
                    case "U":
                        Cliente catlocal = cliDAO.filtrarCliente(vista.getNif());
                        catlocal.setNombre(vista.getNombre());
                        catlocal.setApellidos(vista.getApellidos());
                        catlocal.setCalle(vista.getCalle());
                        catlocal.setPoblacion(vista.getPoblacion());
                        catlocal.setLatitud(vista.getLatitud());
                        catlocal.setLongitud(vista.getLongitud());
                        catlocal.setCalle(vista.getCalle());
                        catlocal.setPoblacion(vista.getPoblacion());
                        catlocal.setProximaVisita(vista.getProximaVisita());
                        cliDAO.EditarCliente(catlocal);
                        break;
                    case "D":
                        cliDAO.removeCliente(vista.getNombre());
                        break;
                }
            }
        }
        for (Object obj : map.get("Pedido")) {
            PedidoVista vista = (PedidoVista) obj;
            if (vista.getFecha().compareTo(ultima) > 0) {
                switch (vista.getOp()) {
                    case "I":
                        //String fecha, String estado, double total
                        peDAO.addPedido(new Pedido(vista.getFechaEntrega(), vista.getEstado(), vista.getTotal()));
                    case "U":
                        Pedido catlocal = peDAO.filtrarPedido(vista.getId());
                        catlocal.setEstado(vista.getEstado());
                        catlocal.setFecha(vista.getFechaEntrega());
                        /*catlocal.setCalle(vista.getCalle());
                        catlocal.setPoblacion(vista.getPoblacion());
                        catlocal.setLatitud(vista.getLatitud());
                        catlocal.setLongitud(vista.getLongitud());
                        catlocal.setCalle(vista.getCalle());
                        catlocal.setPoblacion(vista.getPoblacion());
                        catlocal.setProximaVisita(vista.getProximaVisita());*/
                        peDAO.EditarPedido(catlocal);
                        break;
                    case "D":
                        peDAO.removePedido(vista.getId());
                        break;
                }
            }
        }
        for (Object obj : map.get("PedidoProducto")) {
            PedidoVista vista = (PedidoVista) obj;
            if (vista.getFecha().compareTo(ultima) > 0) {
                switch (vista.getOp()) {
                    case "I":
                        //Faltar
                        //String fecha, String estado, double total
                        // Producto producto, Pedido pedido, double cantidad
                        //peDAO.addPedido(new PedidoProducto(vista.getFechaEntrega(),vista.getEstado(),vista.getTotal()));
                    case "U":
                        Pedido catlocal = peDAO.filtrarPedido(vista.getId());
                        catlocal.setEstado(vista.getEstado());
                        catlocal.setFecha(vista.getFechaEntrega());
                        /*catlocal.setCalle(vista.getCalle());
                        catlocal.setPoblacion(vista.getPoblacion());
                        catlocal.setLatitud(vista.getLatitud());
                        catlocal.setLongitud(vista.getLongitud());
                        catlocal.setCalle(vista.getCalle());
                        catlocal.setPoblacion(vista.getPoblacion());
                        catlocal.setProximaVisita(vista.getProximaVisita());*/
                        peDAO.EditarPedido(catlocal);
                        break;
                    case "D":
                        peDAO.removePedido(vista.getId());
                        break;
                }
            }
        }


    }

    public void guardarDatosBajados(TreeMap<String, ArrayList> map) throws SQLException {
        Categoria cat = null;
        for (Object obj : map.get("HoraBajada")) {
            Horas hora = (Horas) obj;
            hoDAO.addPedido(hora);

        }
        for (Object obj : map.get("Categoria")) {
            cat = (Categoria) obj;
            catDAO.addCategoria(cat);
        }
        for (Object obj : map.get("Productos")) {
            Producto pro = (Producto) obj;
            cat = catDAO.filtrarCategoria(pro.getCategoria().getNombre());
            cat.addProducto(pro);
            catDAO.EditarCategoria(cat);
            proDAO.addProducto(pro);
        }
        for (Object obj : map.get("Usuario")) {
            Usuario usu = (Usuario) obj;
            usuDAO.addUsuario(usu);
        }
        for (Object obj : map.get("Cliente")) {
            Cliente cli = (Cliente) obj;
            Usuario usu = usuDAO.filtrarUsuario(cli.getUsu().getNif());
            usu.addClientes(cli);
            usuDAO.EditarProducto(usu);
            cliDAO.addCliente(cli);
        }
        for (Object obj : map.get("Pedido")) {
            Pedido ped = (Pedido) obj;
            Cliente cli = cliDAO.filtrarCliente(ped.getCliente().getNif());
            ped.setCliente(cli);
            cliDAO.EditarCliente(cli);
            peDAO.addPedido(ped);
        }
        for (Object obj : map.get("PedidoProducto")) {
            PedidoProducto pedPro = (PedidoProducto) obj;
            Pedido ped = peDAO.filtrarPedido(pedPro.getPedido().getId());
            Producto pro = proDAO.filtrarProducto(pedPro.getProducto().getNombre());
            pro.addLiniaPedido(pedPro);
            ped.addLiniaProducto(pedPro);
            peDAO.EditarPedido(ped);
            proDAO.EditarProducto(pro);
            pepoDAO.addPedidoProducto(pedPro);
        }


    }

    public void removeCliente(String nif) throws SQLException {
        cliDAO.removeCliente(nif);

    }


    public List<Cliente> mostrarClientes() throws SQLException {
        List<Cliente> listDao = cliDAO.getClientes();
        return listDao;
    }

    public Cliente filtrarCliente(String nif) throws SQLException {
        return cliDAO.filtrarCliente(nif);
    }

    public void editarCliente(String beforenif, String newnif, String nombre, String apellidos, String poblacion, String calle, String proximaVisita) throws SQLException {
        Cliente client = cliDAO.filtrarCliente(beforenif);
        client.setNif(newnif);
        client.setNombre(nombre);
        client.setApellidos(apellidos);
        client.setProximaVisita(proximaVisita);
        client.setPoblacion(poblacion);
        client.setCalle(calle);
        cliDAO.EditarCliente(client);

    }

    public void crearCliente(String nif, String nombre, String apellidos, String poblacion, String calle, String proximaVisita) {
        Cliente cli = new Cliente(nif, nombre, apellidos, poblacion, calle, proximaVisita);
        cliDAO.addCliente(cli);
    }

    public void crearPedido(String fecha, Cliente cliente, ForeignCollection<PedidoProducto> productos) {
        Pedido pe = new Pedido();

        pe.setLiniaProducto(productos);
        pe.setCliente(cliente);
        pe.setFecha(fecha);
        peDAO.addPedido(pe);

    }

    public String getUltimaSubida() throws SQLException {
        String a = hoDAO.getUltimaSubida().getFecha();
        return hoDAO.getUltimaSubida().getFecha();
    }

    public String getUltimaBajada() throws SQLException {

        return hoDAO.getUltimaBajada().getFecha();
    }

    public void removePedido(int id) throws SQLException {
        peDAO.removePedido(id);
    }

    public List<Pedido> mostrarPedido() throws SQLException {
        return peDAO.getPedidos();
    }

    public List<Producto> mostrarProducto() throws SQLException {
        return proDAO.getPedidos();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void ultimaComanda(Cliente client) {
        Iterator it = client.getPedido().iterator();
        Calendar max = Calendar.getInstance();
        while (it.hasNext()) {
            Pedido pe = (Pedido) it.next();
            String[] fecha = pe.getFecha().split("/");
            //max.set( Calendar.YEAR,fe);
        }
    }

    public void dadesPrueba() {
    }

    private int RequiredSOS() throws SQLException {
        return catDAO.mostrarCategorias().size() + cliDAO.getClientes().size() + pepoDAO.mostrarCategorias().size() + catDAO.mostrarCategorias().size() + usuDAO.getUsuarios().size();
    }

    public void doBajada(String s) {
    }

    ;

}
