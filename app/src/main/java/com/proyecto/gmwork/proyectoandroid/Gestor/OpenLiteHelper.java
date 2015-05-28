package com.proyecto.gmwork.proyectoandroid.Gestor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.proyecto.gmwork.proyectoandroid.Model.Categoria;
import com.proyecto.gmwork.proyectoandroid.Model.CategoriaLog;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.ClienteLog;
import com.proyecto.gmwork.proyectoandroid.Model.Horas;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoProducto;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoProductoLog;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.Model.ProductoLog;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoLog;
import com.proyecto.gmwork.proyectoandroid.Model.UsuarioLog;
import com.proyecto.gmwork.proyectoandroid.R;
import com.proyecto.gmwork.proyectoandroid.controller.PersistencyWebController;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Matthew on 06/05/2015.
 */
public class OpenLiteHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "db.gmwork";
    private static final int DATABASE_VERSION = 1;
    private static Dao<Cliente, Long> daoCli = null;
    private static Dao<Pedido, Long> daoPe = null;
    private static Dao<Usuario, Long> daoUsu = null;
    private static Dao<Categoria, Long> daoCat = null;
    private static Dao<Producto, Long> daoPro = null;
    private static Dao<PedidoProducto, Long> daoProPe = null;
    private static Dao<Horas, Long> daoHora = null;
    private static Dao<ClienteLog, Long> daoCliLog = null;
    private static Dao<PedidoLog, Long> daoPeLog = null;

    public OpenLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION,
                /**
                 * R.raw.ormlite_config is a reference to the ormlite_config.txt file in the
                 * /res/raw/ directory of this project
                 * */
                R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Categoria.class);
            TableUtils.createTableIfNotExists(connectionSource, Cliente.class);
            TableUtils.createTableIfNotExists(connectionSource, Pedido.class);
            TableUtils.createTableIfNotExists(connectionSource, PedidoProducto.class);
            TableUtils.createTableIfNotExists(connectionSource, Producto.class);
            TableUtils.createTableIfNotExists(connectionSource, Usuario.class);
            TableUtils.createTableIfNotExists(connectionSource, PedidoLog.class);
            TableUtils.createTableIfNotExists(connectionSource, ClienteLog.class);
            TableUtils.createTableIfNotExists(connectionSource, ProductoLog.class);
            TableUtils.createTableIfNotExists(connectionSource, CategoriaLog.class);
            TableUtils.createTableIfNotExists(connectionSource, UsuarioLog.class);
            TableUtils.createTableIfNotExists(connectionSource, PedidoProductoLog.class);

            TableUtils.createTableIfNotExists(connectionSource, Horas.class);
            createTriggers("categoria", "CategoriaLog", database);
            createTriggers("cliente", "ClienteLog", database);
            createTriggers("pedido", "PedidoLog", database);
            createTriggers("producto", "ProductoLog", database);
            createTriggers("usuario", "UsuarioLog", database);
            createTriggers("pedidoProducto", "PedidoProductoLog", database);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void borrarLogs() {
        try {
            TableUtils.dropTable(connectionSource, PedidoLog.class, true);
            TableUtils.dropTable(connectionSource, ClienteLog.class, true);
            TableUtils.dropTable(connectionSource, ProductoLog.class, true);
            TableUtils.dropTable(connectionSource, CategoriaLog.class, true);
            TableUtils.dropTable(connectionSource, UsuarioLog.class, true);
            TableUtils.dropTable(connectionSource, PedidoProductoLog.class, true);
            TableUtils.createTableIfNotExists(connectionSource, PedidoLog.class);
            TableUtils.createTableIfNotExists(connectionSource, ClienteLog.class);
            TableUtils.createTableIfNotExists(connectionSource, ProductoLog.class);
            TableUtils.createTableIfNotExists(connectionSource, CategoriaLog.class);
            TableUtils.createTableIfNotExists(connectionSource, UsuarioLog.class);
            TableUtils.createTableIfNotExists(connectionSource, PedidoProductoLog.class);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private void createTriggers(String table, String claseLog, SQLiteDatabase sql) {
        String i = PersistencyWebController.getHora().getFecha();
        try {
            sql.execSQL("DROP TRIGGER IF EXISTS " + table + "_UP");
            sql.execSQL("DROP TRIGGER IF EXISTS " + table + "_IN");
            sql.execSQL("DROP TRIGGER IF EXISTS " + table + "_D");
            sql.execSQL("CREATE TRIGGER " + table + "_UP AFTER UPDATE ON " + table + "\n"
                    + "FOR EACH ROW \n"
                    + "BEGIN\n"
                    + "INSERT INTO " + claseLog + " (Op, fecha,Id" + Character.toUpperCase(table.charAt(0)) + table.substring(1) + ") VALUES('U', DateTime('now') ,NEW.id);\n"
                    + "END;\n");
            sql.execSQL("CREATE TRIGGER " + table + "_IN AFTER INSERT ON " + table + "\n"
                    + "FOR EACH ROW\n"
                    + "BEGIN\n"
                    + "INSERT INTO " + claseLog + " (Op, fecha,Id" + Character.toUpperCase(table.charAt(0)) + table.substring(1) + ") VALUES('I', DateTime('now') ,NEW.id);\n"
                    + "END;\n");
            sql.execSQL("CREATE TRIGGER " + table + "_D AFTER DELETE ON " + table + "\n"
                    + "FOR EACH ROW\n"
                    + "BEGIN\n"
                    + "INSERT INTO " + claseLog + " (Op, fecha,Id" + Character.toUpperCase(table.charAt(0)) + table.substring(1) + ") VALUES('D', DateTime('now') ,OLD.id);\n"
                    + "END;\n");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Categoria.class, false);
            TableUtils.dropTable(connectionSource, Cliente.class, false);
            TableUtils.dropTable(connectionSource, Pedido.class, false);
            TableUtils.dropTable(connectionSource, PedidoProducto.class, false);
            TableUtils.dropTable(connectionSource, Producto.class, false);
            TableUtils.dropTable(connectionSource, Usuario.class, false);
            TableUtils.dropTable(connectionSource, PedidoLog.class, false);
            TableUtils.dropTable(connectionSource, ProductoLog.class, false);
            TableUtils.dropTable(connectionSource, ClienteLog.class, false);
            TableUtils.dropTable(connectionSource, Horas.class, false);
            database.execSQL("DROP TRIGGER categoria_UP");
            database.execSQL("DROP TRIGGER categoria_IN");
            database.execSQL("DROP TRIGGER categoria_D");
            database.execSQL("DROP TRIGGER cliente_UP");
            database.execSQL("DROP TRIGGER cliente_IN");
            database.execSQL("DROP TRIGGER cliente_D");
            database.execSQL("DROP TRIGGER producto_UP");
            database.execSQL("DROP TRIGGER producto_IN");
            database.execSQL("DROP TRIGGER producto_D");
            database.execSQL("DROP TRIGGER usuario_UP");
            database.execSQL("DROP TRIGGER usuario_IN");
            database.execSQL("DROP TRIGGER usuario_D");
            database.execSQL("DROP TRIGGER pedidoProducto_D");
            database.execSQL("DROP TRIGGER PedidoProducto_UP");
            database.execSQL("DROP TRIGGER PedidoProducto_IN");

            //pedidoProducto_D PedidoProducto_UP PedidoProducto_IN
            onCreate(database, connectionSource);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Dao<Cliente, Long> getDAOCliente() throws SQLException {
        if (daoCli == null) {
            daoCli = getDao(Cliente.class);
        }
        return daoCli;

    }

    public Dao<Pedido, Long> getDAOPedido() throws SQLException {
        if (daoPe == null) {
            daoPe = getDao(Pedido.class);
        }
        return daoPe;

    }

    public Dao<Usuario, Long> getDAOUsuario() throws SQLException {
        if (daoUsu == null) {
            daoUsu = getDao(Usuario.class);
        }
        return daoUsu;

    }


    public Dao<Categoria, Long> getDAOCategoria() throws SQLException {
        if (daoCat == null) {
            daoCat = getDao(Categoria.class);
        }
        return daoCat;

    }


    public Dao<Producto, Long> getDAOProducto() throws SQLException {
        if (daoPro == null) {
            daoPro = getDao(Producto.class);
        }
        return daoPro;
    }

    public Dao<PedidoLog, Long> getDAOPedidoLog() throws SQLException {
        if (daoPeLog == null) {
            daoPeLog = getDao(PedidoLog.class);
        }
        return daoPeLog;
    }

    public Dao<ClienteLog, Long> getDAOClienteLog() throws SQLException {
        if (daoCliLog == null) {
            daoCliLog = getDao(ClienteLog.class);
        }
        return daoCliLog;
    }

    public Dao<PedidoProducto, Long> getDAOPedidoProducto() throws SQLException {
        if (daoProPe == null) {
            daoProPe = getDao(PedidoProducto.class);
        }
        return daoProPe;
    }

    public Dao<Horas, Long> getDAOHoras() throws SQLException {
        if (daoHora == null) {
            daoHora = getDao(Horas.class);
        }
        return daoHora;
    }


    public boolean hacerLogin(Usuario usu) throws SQLException {
        Usuario usuario = getDAOUsuario().queryForEq("username", usu.getUsername()).get(0);

        return usuario != null;

    }

    public void dadesPrueba() throws SQLException {
        //(String nif, String nombre, String apellidos, String calle, String poblacion, boolean administrador, String username, String password)
        getDAOUsuario().create(new Usuario("583241A", "Matthew", "dssada", "sdsad", "asdsad", true, "aasdssda", "asdsad"));
        getDAOCategoria().create(new Categoria("Ordenadores1", 2.2));
        //public Producto(String nombre, double precio, byte[] img, boolean inhabilitats, double descuento)
        getDAOProducto().create(new Producto("producto1", 2.2, "", false, 2.2));
        //public Cliente(String nif, String nombre, String apellidos, String poblacion,String calle, Date proximaVisita) {
        getDAOCliente().create(new Cliente("583241A", "sadsa", "sadsad", "sadsad", "sdasd", "20/22/22"));
    }


    public void borrarBD() {
        try {
            TableUtils.dropTable(connectionSource, Categoria.class, false);
            TableUtils.dropTable(connectionSource, Cliente.class, false);
            TableUtils.dropTable(connectionSource, Pedido.class, false);
            TableUtils.dropTable(connectionSource, PedidoProducto.class, false);

            TableUtils.dropTable(connectionSource, Producto.class, false);

            TableUtils.dropTable(connectionSource, Usuario.class, false);
            TableUtils.dropTable(connectionSource, PedidoLog.class, false);
            TableUtils.dropTable(connectionSource, ProductoLog.class, false);
            TableUtils.dropTable(connectionSource, ClienteLog.class, false);
            TableUtils.dropTable(connectionSource, Horas.class, false);
            TableUtils.createTableIfNotExists(connectionSource, Categoria.class);
            TableUtils.createTableIfNotExists(connectionSource, Cliente.class);
            TableUtils.createTableIfNotExists(connectionSource, Pedido.class);
            TableUtils.createTableIfNotExists(connectionSource, PedidoProducto.class);
            TableUtils.createTableIfNotExists(connectionSource, Producto.class);
            TableUtils.createTableIfNotExists(connectionSource, Usuario.class);
            TableUtils.createTableIfNotExists(connectionSource, PedidoLog.class);
            TableUtils.createTableIfNotExists(connectionSource, ClienteLog.class);
            TableUtils.createTableIfNotExists(connectionSource, ProductoLog.class);
            TableUtils.createTableIfNotExists(connectionSource, CategoriaLog.class);
            TableUtils.createTableIfNotExists(connectionSource, UsuarioLog.class);
            TableUtils.createTableIfNotExists(connectionSource, PedidoProductoLog.class);

            TableUtils.createTableIfNotExists(connectionSource, Horas.class);
            createTriggers("categoria", "CategoriaLog", this.getWritableDatabase());
            createTriggers("cliente", "ClienteLog", this.getWritableDatabase());
            createTriggers("pedido", "PedidoLog", this.getWritableDatabase());
            createTriggers("producto", "ProductoLog", this.getWritableDatabase());
            createTriggers("usuario", "UsuarioLog", this.getWritableDatabase());
            createTriggers("pedidoProducto", "PedidoProductoLog", this.getWritableDatabase());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
