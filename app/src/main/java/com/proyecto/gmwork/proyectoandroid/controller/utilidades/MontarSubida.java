package com.proyecto.gmwork.proyectoandroid.controller.utilidades;

import com.google.gson.Gson;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.ClienteLog;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoLog;
import com.proyecto.gmwork.proyectoandroid.controller.dao.ClienteDAOController;
import com.proyecto.gmwork.proyectoandroid.controller.dao.PedidoDAOController;

import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Mateo on 23/05/2015.
 */
public class MontarSubida {
    public static List<String[]> montarCliente(ClienteDAOController dao,String ultimabajada) throws SQLException {
        List<ClienteLog> cliLog = dao.getLog();
        ClienteLog logCli = null;
        Gson gson = new Gson();
        Cliente cli = null;
        List<String[]> cliente = new ArrayList<String[]>();
        TreeMap<String,List<String[]>> map = new TreeMap<String,List<String[]>>();
        DateTime ultimaBajada = DateTime.parse(ultimabajada);
        for (int i = 0; i < cliLog.size(); i++) {
            logCli = cliLog.get(i);
            if (DateTime.parse(logCli.getFecha()).compareTo(ultimaBajada) > 0) {
                switch (logCli.getOperacion()) {
                    case "I":
                        cli = dao.filtrarID(logCli.getIdCliente());
                        cliente.add(new String[]{"POST", "cliente", gson.toJson(cli)});
                        break;
                    case "U":
                        cli = dao.filtrarID(logCli.getIdCliente());
                        cliente.add(new String[]{"PUT","cliente/"+logCli.getIdCliente()+"",gson.toJson(cli)});

                        break;
                    case "D":
                        cliente.add(new String[]{"DELETE","cliente",String.valueOf(cliLog.get(i).getIdCliente())});
                        break;
                }
            }
        }
        return cliente;
    }
    public static List<String[]> montarPedido(PedidoDAOController dao,String ultimabajada) throws SQLException {
        List<PedidoLog> cliLog = dao.getPedidosLog();
        PedidoLog logCli = null;
        Gson gson = new Gson();
        Pedido cli = null;
        List<String[]> cliente = new ArrayList<String[]>();
        TreeMap<String,List<String[]>> map = new TreeMap<String,List<String[]>>();
        DateTime ultimaBajada = DateTime.parse(ultimabajada);
        for (int i = 0; i < cliLog.size(); i++) {
            logCli = cliLog.get(i);
            if (DateTime.parse(logCli.getFecha()).compareTo(ultimaBajada) > 0) {
                switch (logCli.getOperacion()) {
                    case "I":
                        cli = dao.filtrarPedido(logCli.getIdPedido());
                        cliente.add(new String[]{"POST", "cliente", gson.toJson(cli)});
                        break;
                    case "U":
                        cli = dao.filtrarPedido(logCli.getIdPedido());
                        cliente.add(new String[]{"PUT","cliente",gson.toJson(cli)});

                        break;
                    case "D":
                        cliente.add(new String[]{"DELETE","cliente",String.valueOf(cliLog.get(i).getIdPedido())});
                        break;
                }
            }
        }
        return cliente;
    }
}
