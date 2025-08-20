public class Main {
    public static void main(String[] args) throws Exception {
        Consultas.topClientesConMasFacturas(10);
        Consultas.topClientesQueMasGastaron(10);
        Consultas.topMonedasMasUsadas(10);
        Consultas.topProveedoresDeProductos(10);
        Consultas.productosMasVendidos(10);
        Consultas.productosMenosVendidos(10);
        Consultas.detalleFactura(5); //cambiar por el id de la factura que se quiere buscar
        Consultas.facturasOrdenadasPorTotal();
       Consultas.iva10PorFactura(1); //cambiar por el id de la factura que se quiere buscar
    }
}
