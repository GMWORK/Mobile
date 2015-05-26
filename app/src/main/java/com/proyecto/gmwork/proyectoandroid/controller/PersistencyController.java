package com.proyecto.gmwork.proyectoandroid.controller;


import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
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
import com.proyecto.gmwork.proyectoandroid.Model.mapping.PedidoProductoVista;
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
import com.proyecto.gmwork.proyectoandroid.controller.utilidades.GPSTracker;
import com.proyecto.gmwork.proyectoandroid.controller.utilidades.MontarSubida;
import com.proyecto.gmwork.proyectoandroid.controller.utilidades.ThreadActualizarDownloadLogs;
import com.proyecto.gmwork.proyectoandroid.controller.utilidades.ThreadActualizarUpload;
import com.proyecto.gmwork.proyectoandroid.controller.utilidades.encodePassword;

import org.joda.time.DateTime;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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


    public PersistencyController(Context context) {
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

    public void actualizarDatosLocalesActivar() {
        try {
            actualizarDatosLocales(perWeb.actualizarDatos());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Pedido filtrarPedido(long id) {
        return peDAO.filtrarPedido(id);
    }

    public void guardarDatosBajadosActivar() {
        if (RequiredSOS() == 0) {
            perWeb.comprovarSOS(isNetworkAvailable());
        }
    }

    public boolean hacerLogin(String username, String password) {
        Usuario user = new Usuario();
        user.setUsername(username);
        user.setPassword(encodePassword.encodePass(password));

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

    public void subirDatosLocales() {
        TreeMap<String, List<String[]>> map = new TreeMap<String, List<String[]>>();

        map.put("cliente", MontarSubida.montarCliente(cliDAO, getUltimaSubida()));
        map.put("pedido", MontarSubida.montarPedido(peDAO, getUltimaSubida()));
        perWeb.subirDatosLocales(map);


    }

    public void guardarDatosBajados(TreeMap<String, ArrayList> map) {
        try {
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
                // pepoDAO.addPedidoProducto(pedPro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.borrarlogs();
        }
    }

    private void guardarUltimaBajada() {
    }

    private void borrarlogs() {
        bd.borrarLogs();
    }

    public void actualizarDatosLocales(TreeMap<String, ArrayList> map) {
        try {
            Categoria cat = null;
            DateTime ultima = DateTime.parse(getUltimaBajada());
            for (Object obj : map.get("HoraBajada")) {
                Horas hora = (Horas) obj;
                hoDAO.guardarHoraBajada(hora);
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
                            Producto pro = new Producto();
                            pro.setImg(vista.getImg());
                            pro.setDescuento(vista.getDescuento());
                            pro.setNombre(vista.getNombre());
                            pro.setPrecio(vista.getPrecio());
                            Categoria catPro = catDAO.filtrarCategoria(vista.getCategoriaid());
                            catPro.addProducto(pro);
                            pro.setCategoria(catPro);
                            catDAO.EditarCategoria(cat);
                            proDAO.addProducto(pro);
                            break;
                        case "U":
                            Producto catlocal = proDAO.filtrarProducto(vista.getNombre());
                            catlocal.setNombre(vista.getNombre());
                            catlocal.setDescuento(vista.getDescuento());
                            catlocal.setImg(vista.getImg());
                            catlocal.setInhabilitats(vista.isInhabilitats());
                            catlocal.setPrecio(vista.getPrecio());
                            Categoria catProEdit = catDAO.filtrarCategoria(vista.getCategoriaid());
                            cat.addProducto(catlocal);
                            catlocal.setCategoria(catProEdit);
                            catDAO.EditarCategoria(catProEdit);
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
                        case "I": {
                            usuDAO.addUsuario(new Usuario(vista.getNif(), vista.getNombre(), vista.getApellidos(), vista.getCalle(), vista.getPoblacion(), vista.isAdministrador(), vista.getUsername(), vista.getPassword()));
                        }
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
                            Cliente cli = new Cliente();
                            cli.setNif(vista.getNif());
                            cli.setNombre(vista.getNombre());
                            cli.setPoblacion(vista.getPoblacion());
                            cli.setCalle(vista.getCalle());
                            cli.setImg(vista.getImg());
                            cli.setLatitud(vista.getLatitud());
                            cli.setLongitud(vista.getLongitud());
                            cli.setProximaVisita(String.valueOf(vista.getProximaVisita()));
                            Usuario usu = usuDAO.filtrarUsuario(vista.getUsuarioid());
                            cli.setUsu(usu);
                            usu.addClientes(cli);
                            usuDAO.EditarProducto(usu);
                            cliDAO.addCliente(cli);
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
                            Usuario usuEdit = usuDAO.filtrarUsuario(vista.getUsuarioid());
                            catlocal.setUsu(usuEdit);
                            usuDAO.EditarProducto(usuEdit);
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
                            Pedido ped = new Pedido();
                            ped.setTotal(vista.getTotal());
                            ped.setFechaEntrega(vista.getFechaEntrega());
                            ped.setEstado(vista.getEstado());
                            Cliente cli = cliDAO.filtrarCliente(vista.getClienteid());
                            ped.setCliente(cli);
                            cli.addPedido(ped);
                            cliDAO.EditarCliente(cli);
                            peDAO.addPedido(ped);
                        case "U":
                            Pedido catlocal = peDAO.filtrarPedido(vista.getId());
                            catlocal.setTotal(vista.getTotal());
                            catlocal.setFechaEntrega(vista.getFechaEntrega());
                            catlocal.setEstado(vista.getEstado());
                            Cliente cliEdit = cliDAO.filtrarCliente(vista.getClienteid());
                            catlocal.setCliente(cliEdit);
                            cliEdit.addPedido(catlocal);
                            cliDAO.EditarCliente(cliEdit);
                            peDAO.EditarPedido(catlocal);
                            break;
                        case "D":
                            peDAO.removePedido(vista.getId());
                            break;
                    }
                }
            }
            for (Object obj : map.get("PedidoProducto")) {
                PedidoProductoVista vista = (PedidoProductoVista) obj;
                if (vista.getFecha().compareTo(ultima) > 0) {
                    switch (vista.getOp()) {
                        case "I":
                            PedidoProducto prePro = new PedidoProducto();
                            prePro.setCantidad(Double.parseDouble(vista.getCantidad()));
                            Pedido pe = peDAO.filtrarPedido(vista.getPedidoid());
                            Producto pro = proDAO.filtrarProducto(vista.getProductoid());
                            pe.addLiniaProducto(prePro);
                            pro.addLiniaPedido(prePro);
                            proDAO.EditarProducto(pro);
                            peDAO.EditarPedido(pe);
                        case "U":
                            PedidoProducto pedProEdit = pepoDAO.filtrarPedidoProductoPedido(vista.getPedidoid());

                            pedProEdit.setCantidad(Double.parseDouble(vista.getCantidad()));
                            pepoDAO.EditarPedidoProducto(pedProEdit);
                            break;
                        case "D":
                            PedidoProducto pedProEs = pepoDAO.filtrarPedidoProductoPedido(vista.getPedidoid());
                            pepoDAO.removePedidoProductodePedido(pedProEs);
                            break;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.borrarlogs();
        }

    }

    public void removeCliente(String nif) {
        cliDAO.removeCliente(nif);

    }


    public List<Cliente> mostrarClientes(String usuario) {
        List<Cliente> listDao = cliDAO.getClientes(usuario);
        return listDao;
    }

    public Cliente filtrarCliente(String nif) {
        return cliDAO.filtrarCliente(nif);
    }

    public void editarCliente(String beforenif, String newnif, String nombre, String apellidos, String poblacion, String calle, String proximaVisita) {
        Cliente client = cliDAO.filtrarCliente(beforenif);
        client.setNif(newnif);
        client.setNombre(nombre);
        client.setApellidos(apellidos);
        client.setProximaVisita(proximaVisita);
        client.setPoblacion(poblacion);
        client.setCalle(calle);
        cliDAO.EditarCliente(client);

    }

    public void crearCliente(String usuario, String nif, String nombre, String apellidos, String poblacion, String calle, String proximaVisita) {
        Cliente cli = new Cliente(nif, nombre, apellidos, poblacion, calle, proximaVisita);
        Usuario usu = usuDAO.filtrarUser(usuario);
        cli.setUsu(usu);
        cliDAO.addCliente(cli);
    }

    public void crearPedido(String fecha, String cliente, AdapterListPedidoProductos productos) {
        Pedido ped = new Pedido();
        Cliente cli = cliDAO.filtrarCliente(cliente);
        ped.setCliente(cli);
        ped.setEstado("procesando");
        ped.setFechaEntrega(fecha);
        cliDAO.EditarCliente(cli);
        peDAO.addPedido(ped);
        double total = 0;
        for (int i = 0; i < productos.getCount(); i++) {
            PedidoProducto pedPro = productos.getItem(i);
            //ped = peDAO.filtrarPedido(pedPro.getPedido().getId());
            Producto pro = proDAO.filtrarProducto(pedPro.getProducto().getNombre());
            total += pro.getPrecioDescontado();
            pro.addLiniaPedido(pedPro);
            ped = peDAO.filtrarPedido(ped.getId());
            pedPro.setPedido(ped);
            ped.addLiniaProducto(pedPro);
            peDAO.EditarPedido(ped);
            proDAO.EditarProducto(pro);

        }
        ped.setTotal(total);
        peDAO.addPedido(ped);

    }

    public String getUltimaSubida() {
        String a = hoDAO.getUltimaSubida().getFecha();
        return hoDAO.getUltimaSubida().getFecha();
    }

    public String getUltimaBajada() {

        return hoDAO.getUltimaBajada().getFecha();
    }

    public void removePedido(long id) {
        peDAO.removePedido(id);
    }

    public List<Pedido> mostrarPedido(String username) {
        return peDAO.getPedidos(username);
    }

    public List<Producto> mostrarProducto() {
        return proDAO.getProductos();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public ArrayList<Pedido> ultimaComanda(String nif) {
        ArrayList<Pedido> listped = new ArrayList<Pedido>();
        Cliente cli = cliDAO.filtrarCliente(nif);

        Iterator it = cli.getPedido().iterator();
        Calendar max = Calendar.getInstance();
        DateTime date = DateTime.parse("1999-2-2");
        Pedido ped = null;
        while (it.hasNext()) {
            Pedido pe = (Pedido) it.next();
            if (DateTime.parse(pe.getFechaEntrega()).compareTo(date) > 0) {
                date = DateTime.parse(pe.getFechaEntrega());
                ped = pe;
            }
            //max.set( Calendar.YEAR,fe);
        }
        listped.add(ped);
        return listped;
    }

    public void dadesPrueba() {
    }

    private int RequiredSOS() {
        return catDAO.mostrarCategorias().size() + cliDAO.getClientes().size() + pepoDAO.mostrarCategorias().size() + catDAO.mostrarCategorias().size() + usuDAO.getUsuarios().size();
    }

    public void doBajada(String s) {
    }

    public List<String> nombresProductos() {

        List<Producto> productos = proDAO.getProductossBaja();
        List<String> nombres = new ArrayList<String>();
        for (int i = 0; i < productos.size(); i++) {
            nombres.add(productos.get(i).getNombre());
        }
        return nombres;
    }

    public List<PedidoProducto> mostrarPedidosProducto() {
        return pepoDAO.mostrarCategorias();
    }

    public AdapterListPedidoProductos añadirRegistroPedidoProductoAdapter(AdapterListPedidoProductos adapter, String producto, int value) {

        AdapterListPedidoProductos pojo = adapter;
        PedidoProducto ped = new PedidoProducto();
        ped.setCantidad(value);
        Producto pro = null;
        pro = proDAO.filtrarProducto(producto);
        ped.setProducto(pro);
        pojo.UpdateArray(ped);
        pojo.add(ped);
        return pojo;
    }

    public LatLng getMiUbicacion() {
        GPSTracker tracker = new GPSTracker(con);

        return new LatLng(tracker.getLatitude(), tracker.getLongitude());
    }

    public List<Cliente> getCLienteCercanos(String usuario) {
        GPSTracker tracker = new GPSTracker(con);
        List<Cliente> clientes = mostrarClientes(usuario);
        List<Cliente> aVisitar = new ArrayList<Cliente>();
        DateTime hora = DateTime.parse(perWeb.getHora().getFecha());
        Cliente cli = null;
        for (int i = 0; i < clientes.size(); i++) {
            cli = clientes.get(i);
            if (tracker.distance(clientes.get(i).getLatitud(), clientes.get(i).getLongitud()) < 1.0 && !cli.isBaja() && DateTime.parse(cli.getProximaVisita()).compareTo(hora) > 0) {
                aVisitar.add(cli);
            }
        }
        return aVisitar;
    }


    public void editarPedido(long pe, String nif, String fecha, AdapterListPedidoProductos pedidos) {
        Pedido ped = peDAO.filtrarPedido(pe);
        Cliente cli = cliDAO.filtrarCliente(nif);
        ped.setCliente(cli);
        ped.setEstado("procesando");
        ped.setFechaEntrega(fecha);
        cliDAO.EditarCliente(cli);
        peDAO.EditarPedido(ped);
        double total = 0;
        for (int i = 0; i < pedidos.getCount(); i++) {
            PedidoProducto pedPro = pedidos.getItem(i);
            //ped = peDAO.filtrarPedido(pedPro.getPedido().getId());
            Producto pro = proDAO.filtrarProducto(pedPro.getProducto().getNombre());
            total += pro.getPrecioDescontado();
            pro.addLiniaPedido(pedPro);

            ped = peDAO.filtrarPedido(ped.getId());
            pedPro.setPedido(ped);
            ped.addLiniaProducto(pedPro);
            peDAO.EditarPedido(ped);
            proDAO.EditarProducto(pro);

        }
        ped.setTotal(total);
        peDAO.addPedido(ped);

    }

    public List<PedidoProducto> getListPedidoProductoFromPedido(ForeignCollection<PedidoProducto> a) {
        List<PedidoProducto> pelist = new ArrayList<PedidoProducto>();
        Iterator i = a.iterator();
        while (i.hasNext()) {
            PedidoProducto pePro = (PedidoProducto) i.next();
            pelist.add(pePro);
        }
        return pelist;

    }


    public String[] clientesAVisitar(TreeMap<String, Boolean> checked) {
        String[] clientes = new String[checked.size()];
        Iterator chec = checked.keySet().iterator();
        int i = 0;
        while (chec.hasNext()) {
            String nif = (String) chec.next();
            if (checked.get(nif)) {
                clientes[i] = nif;
            }
            i++;
        }
        return clientes;
    }

    public List<Cliente> getClienteseleccionados(String[] clienteses) {
        List<Cliente> aVisitar = new ArrayList<Cliente>();
        List<Cliente> clientes = cliDAO.getClientes();
        Cliente cli = null;
        for (int i = 0; i < clienteses.length; i++) {
            cli = clientes.get(i);
            aVisitar.add(filtrarCliente(clienteses[i]));
        }
        return aVisitar;
    }


    public void close() {

    }

    public void actualizarFechaCliente(String nif, String fecha) {
        Cliente cli = cliDAO.filtrarCliente(nif);
        cli.setProximaVisita(fecha);
        cliDAO.EditarCliente(cli);

    }

    public ArrayList<Cliente> filtroCliente(String campo, String valor, String username) {
        ArrayList<Cliente> cli = null;
        cli = cliDAO.filtrarClienteCampo(campo, valor, username);
        return cli;

    }
}
