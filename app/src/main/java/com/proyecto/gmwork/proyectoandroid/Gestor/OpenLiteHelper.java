package com.proyecto.gmwork.proyectoandroid.Gestor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.proyecto.gmwork.proyectoandroid.Model.Categoria;
import com.proyecto.gmwork.proyectoandroid.Model.Cliente;
import com.proyecto.gmwork.proyectoandroid.Model.ClienteLog;
import com.proyecto.gmwork.proyectoandroid.Model.Pedido;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoProducto;
import com.proyecto.gmwork.proyectoandroid.Model.Producto;
import com.proyecto.gmwork.proyectoandroid.Model.ProductoLog;
import com.proyecto.gmwork.proyectoandroid.Model.Usuario;
import com.proyecto.gmwork.proyectoandroid.Model.PedidoLog;
import com.proyecto.gmwork.proyectoandroid.R;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Matthew on 06/05/2015.
 */
public class OpenLiteHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "db.lite";
    private static final int DATABASE_VERSION = 1;
    private static Dao<Cliente, Long> daoCli = null;
    private static Dao<Pedido, Long> daoPe = null;
    private static Dao<Usuario, Long> daoUsu = null;
    private static Dao<Categoria, Long> daoCat = null;
    private static Dao<Producto, Long> daoPro = null;
    private static Dao<PedidoProducto, Long> daoProPe = null;


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
            TableUtils.createTable(connectionSource, Categoria.class);
            TableUtils.createTable(connectionSource, Cliente.class);
            TableUtils.createTable(connectionSource, Pedido.class);
            TableUtils.createTable(connectionSource, PedidoProducto.class);
            TableUtils.createTable(connectionSource, Producto.class);
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, PedidoLog.class);
            TableUtils.createTable(connectionSource, ClienteLog.class);
            TableUtils.createTable(connectionSource, ProductoLog.class);
            database.execSQL("CREATE TRIGGER categoria_UP AFTER UPDATE ON categoria\n" +
                    "    FOR EACH ROW\n" +
                    "    BEGIN\n" +
                    "            INSERT INTO CategoriaLog (operacion, fecha,IdCategoria) VALUES('U',  CURRENT_TIMESTAMP ,OLD.id);\n" +
                    "    END;\n" +
                    "CREATE TRIGGER categoria_IN AFTER INSERT ON categoria\n" +
                    "                    \"    FOR EACH ROW\\n\" +\n" +
                    "                    \"    BEGIN\\n\" +\n" +
                    "                    \"            INSERT INTO CategoriaLog (operacion, fecha,IdCategoria) VALUES('I',  CURRENT_TIMESTAMP ,OLD.id);\\n\" +\n" +
                    "                    \"    END;\n" +
                    "\n" +
                    "CREATE TRIGGER categoria_D AFTER DELETE ON categoria\n" +
                    "                    \"    FOR EACH ROW\\n\" +\n" +
                    "                    \"    BEGIN\\n\" +\n" +
                    "                    \"            INSERT INTO CategoriaLog (operacion, fecha,IdCategoria) VALUES('D',  CURRENT_TIMESTAMP ,OLD.id);\\n\" +\n" +
                    "                    \"    END;\n" +
                    "\n" +
                    "CREATE TRIGGER cliente_UP AFTER UPDATE ON cliente\n" +
                    "    FOR EACH ROW\n" +
                    "    BEGIN\n" +
                    "            INSERT INTO ClienteLog (operacion, fecha,idCliente) VALUES('U',  CURRENT_TIMESTAMP ,OLD.id);\n" +
                    "    END;\n" +
                    "CREATE TRIGGER cliente_IN AFTER INSERT ON cliente\n" +
                    "    FOR EACH ROW\n" +
                    "    BEGIN\n" +
                    "            INSERT INTO ClienteLog (operacion, fecha,idCliente) VALUES('I',  CURRENT_TIMESTAMP ,OLD.id);\n" +
                    "    END;\n" +
                    "CREATE TRIGGER cliente_D AFTER DELETE ON cliente\n" +
                    "    FOR EACH ROW\n" +
                    "    BEGIN\n" +
                    "            INSERT INTO ClienteLog (operacion, fecha,idCliente) VALUES('D',  CURRENT_TIMESTAMP ,OLD.id);\n" +
                    "    END;\n" +
                    "CREATE TRIGGER producto_UP AFTER UPDATE ON producto\n" +
                    "    FOR EACH ROW\n" +
                    "    BEGIN\n" +
                    "            INSERT INTO ProductoLog (operacion, fecha,IdProducto) VALUES('U',  CURRENT_TIMESTAMP ,OLD.id);\n" +
                    "     END;\n" +
                    "CREATE TRIGGER producto_IN AFTER INSERT ON producto\n" +
                    "    FOR EACH ROW\n" +
                    "    BEGIN\n" +
                    "            INSERT INTO ProductoLog (operacion, fecha,IdProducto) VALUES('I',  CURRENT_TIMESTAMP ,OLD.id);\n" +
                    "     END;\n" +
                    "CREATE TRIGGER producto_D AFTER DELETE ON producto\n" +
                    "    FOR EACH ROW\n" +
                    "    BEGIN\n" +
                    "            INSERT INTO ProductoLog (operacion, fecha,IdProducto) VALUES('D',  CURRENT_TIMESTAMP ,OLD.id);\n" +
                    "     END;\n" +
                    "CREATE TRIGGER usuario_UP AFTER UPDATE ON usuario\n" +
                    "    FOR EACH ROW\n" +
                    "    BEGIN\n" +
                    "            INSERT INTO UsuarioLog (operacion, fecha,idUsuario) VALUES('U',  CURRENT_TIMESTAMP ,OLD.id);\n" +
                    "     END;" +
                    "CREATE TRIGGER usuario_IN AFTER INSERT ON usuario\n" +
                    "    FOR EACH ROW\n" +
                    "    BEGIN\n" +
                    "            INSERT INTO UsuarioLog (operacion, fecha,idUsuario) VALUES('I',  CURRENT_TIMESTAMP ,OLD.id);\n" +
                    "     END;" +
                    "CREATE TRIGGER usuario_D AFTER DELETE ON usuario\n" +
                    "    FOR EACH ROW\n" +
                    "    BEGIN\n" +
                    "            INSERT INTO UsuarioLog (operacion, fecha,idUsuario) VALUES('D',  CURRENT_TIMESTAMP ,OLD.id);\n" +
                    "     END;");

            //getDAOProducto().create(new Producto("Producto1", 2.2, null, false, 2.2));

            //trigger
            /*
            database.execSQL(
                    "\n" +
                    "CREATE TRIGGER pedido_UP AFTER UPDATE ON pedido\n" +
                    "    FOR EACH ROW\n" +
                    "    BEGIN\n" +
                    "        IF NEW.estado <> OLD.estado or New.fecha <> OLD.fecha THEN  \n" +
                    "            INSERT INTO pedido_log (operacion, fecha,idPedido) VALUES('update',  CURRENT_TIMESTAMP ,OLD.id);\n" +
                    "        END IF;\n" +
                    "    END;\n" );
            database.execSQL(
                    "create trigger pedido_DE\n" +
                    "after delete on pedido\n" +
                    "for each row\n" +
                    "begin" +
                    "INSERT INTO pedido_log (operacion, fecha,idPedido) VALUES('delete',  CURRENT_TIMESTAMP ,OLD.id);\n" +
                    "tend//");
            database.execSQL(
                    "create trigger pedido_IN\n" +
                    "after insert on pedido\n" +
                    "for each row\n" +
                    "begin\n" +
                    " INSERT INTO pedido_log (operacion, fecha,idPedido) VALUES('insert',  CURRENT_TIMESTAMP ,NEW.id);\n" +
                    "end//"
                    );
*/
        } catch (SQLException e) {
            e.printStackTrace();
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
            onCreate(database, connectionSource);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void SOSCategoria(ArrayList map) throws SQLException {

        for (Object obj : map) {
            Categoria usuario = (Categoria) obj;
            getDAOCategoria().create(usuario);
            Log.i("Nombre", usuario.getNombre());
        }
    }
    public void SOSCliente(ArrayList map) throws SQLException {

        for (Object obj : map) {
            Cliente cli = (Cliente) obj;
            getDAOCliente().create(cli);
            Log.i("Nombre", cli.getNombre());
        }
    }
    public void SOSProducto(ArrayList map) throws SQLException {
        for (Object obj : map) {
            Producto pro = (Producto) obj;
            getDAOProducto().create(pro);
            Log.i("Producto", pro.getNombre());
        }
    }
    public void SOSPedido(ArrayList map) throws SQLException {
        for (Object obj : map) {
            Pedido pro = (Pedido) obj;
            getDAOPedido().create(pro);
            Log.i("Pedido", pro.getFecha());
        }
    }
    public void SOSUsuario(ArrayList map) throws SQLException {
        for (Object obj : map) {
            Usuario usu = (Usuario) obj;
            getDAOUsuario().create(usu);
        }
    }

    public void SOSLiniaProducto(ArrayList map) throws SQLException {
        for (Object obj : map) {
            PedidoProducto usu = (PedidoProducto) obj;
            getDAOPedidoProducto().create(usu);
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
    public Dao<PedidoProducto, Long> getDAOPedidoProducto() throws SQLException {
        if (daoProPe == null) {
            daoProPe = getDao(PedidoProducto.class);
        }
        return daoProPe;
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
        getDAOProducto().create(new Producto("producto1", 2.2, new byte[]{02}, false, 2.2));
        //public Cliente(String nif, String nombre, String apellidos, String poblacion,String calle, Date proximaVisita) {
        getDAOCliente().create(new Cliente("583241A", "sadsa", "sadsad", "sadsad", "sdasd", "20/22/22"));
    }



}
