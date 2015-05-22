package com.proyecto.gmwork.proyectoandroid.controller;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.j256.ormlite.dao.ForeignCollection;
import com.proyecto.gmwork.proyectoandroid.Model.Categoria;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.Horas;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoProducto;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;
import com.proyecto.gmwork.proyectoandroid.Gestor.OpenLiteHelper;
import com.proyecto.gmwork.proyectoandroid.controller.dao.CategoriaDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.dao.ClienteDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.dao.HoraDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.dao.PedidoDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.dao.PedidoProductoDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.dao.ProductoDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.dao.UsuarioDAOController;

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

    public String getUltimaDescarga() throws SQLException {
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
