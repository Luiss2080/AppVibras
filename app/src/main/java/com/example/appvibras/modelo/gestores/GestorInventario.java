package com.example.appvibras.modelo.gestores;

import android.content.Context;
import com.example.appvibras.modelo.base.BaseDatos;
import com.example.appvibras.modelo.entidades.MovimientoStock;
import com.example.appvibras.modelo.entidades.Producto;
import java.util.List;

/**
 * Clase gestora para la lógica de entradas y salidas de stock.
 */
public class GestorInventario {

    private BaseDatos db;

    public GestorInventario(Context contexto) {
        this.db = BaseDatos.obtenerInstancia(contexto);
    }

    /**
     * Registra una entrada de stock para un producto.
     */
    public boolean registrarEntrada(int idProducto, int cantidad) {
        if (cantidad <= 0) return false;

        Producto producto = db.productoDao().obtenerPorId(idProducto);
        if (producto != null) {
            // Actualizar stock del producto
            producto.setStockActual(producto.getStockActual() + cantidad);
            db.productoDao().actualizar(producto);

            // Registrar movimiento
            MovimientoStock movimiento = new MovimientoStock(idProducto, cantidad, "ENTRADA", System.currentTimeMillis());
            return db.movimientoStockDao().insertar(movimiento) > 0;
        }
        return false;
    }

    /**
     * Registra una salida de stock (venta) para un producto.
     */
    public boolean registrarSalida(int idProducto, int cantidad) {
        if (cantidad <= 0) return false;

        Producto producto = db.productoDao().obtenerPorId(idProducto);
        if (producto != null && producto.getStockActual() >= cantidad) {
            // Actualizar stock del producto
            producto.setStockActual(producto.getStockActual() - cantidad);
            db.productoDao().actualizar(producto);

            // Registrar movimiento
            MovimientoStock movimiento = new MovimientoStock(idProducto, cantidad, "SALIDA", System.currentTimeMillis());
            return db.movimientoStockDao().insertar(movimiento) > 0;
        }
        return false;
    }

    public List<MovimientoStock> obtenerHistorial() {
        return db.movimientoStockDao().obtenerTodos();
    }
}
