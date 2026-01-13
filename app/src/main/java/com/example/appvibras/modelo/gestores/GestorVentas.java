package com.example.appvibras.modelo.gestores;

import android.content.Context;
import com.example.appvibras.modelo.base.BaseDatos;
import com.example.appvibras.modelo.entidades.DetalleVenta;
import com.example.appvibras.modelo.entidades.Producto;
import com.example.appvibras.modelo.entidades.Venta;
import java.util.List;

/**
 * Gestor para la lógica de negocio de ventas y actualización de stock.
 */
public class GestorVentas {
    private BaseDatos db;

    public GestorVentas(Context contexto) {
        this.db = BaseDatos.obtenerInstancia(contexto);
    }

    /**
     * Registra una venta completa, descuenta stock y guarda el detalle.
     */
    public boolean realizarVenta(int idCliente, List<DetalleVenta> detalles) {
        double total = 0;
        for (DetalleVenta d : detalles) {
            total += d.getPrecioUnitario() * d.getCantidad();
            
            // Verificar y actualizar stock
            Producto p = db.productoDao().obtenerPorId(d.getIdProducto());
            if (p == null || p.getStockActual() < d.getCantidad()) return false;
            p.setStockActual(p.getStockActual() - d.getCantidad());
            db.productoDao().actualizar(p);
        }

        Venta nuevaVenta = new Venta(idCliente, System.currentTimeMillis(), total);
        long idVenta = db.ventaDao().insertar(nuevaVenta);

        if (idVenta > 0) {
            for (DetalleVenta d : detalles) {
                d.setIdVenta((int) idVenta);
            }
            db.ventaDao().insertarDetalles(detalles);
            return true;
        }
        return false;
    }

    public List<Venta> obtenerTodas() {
        return db.ventaDao().obtenerTodas();
    }
}
