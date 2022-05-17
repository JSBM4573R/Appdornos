package usa.sesion1.adornostienda;

import java.util.ArrayList;

public class Proveedor {
    public static ArrayList<Producto> cargarProductos = getProductos();

    public static ArrayList<Producto> getProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Arbol de navidad", 250000, R.drawable.arbolnavidad));
        productos.add(new Producto("Estrella de navidad", 24000, R.drawable.estrella));
        productos.add(new Producto("Guirnalda", 250000, R.drawable.guirnalda));

        return productos;
    }

    public static ArrayList<Sucursal> getSucursales() {
        ArrayList<Sucursal> sucursales = new ArrayList<>();
        sucursales.add(
                new Sucursal("Sucursal Titan Plaza ", " Av. Boyacá #80-94", 4.674545, -74.143996,
                        R.drawable.sucursal1));

        sucursales.add(
                new Sucursal("Sucursal Iserra 100 ", " Tv. 55 ## 98A-66", 4.629551, -74.135506,
                        R.drawable.sucursal2));

        sucursales.add(
                new Sucursal("Sucursal Hda Sta Barbara ", " Ak. 7 ##115 - 60", 4.719458, -74.072141,
                        R.drawable.sucursal3));

        sucursales.add(
                new Sucursal("Sucursal Centro Mayor ", " Cl. 38A Sur #34d-51", 4.719458, -74.072141,
                        R.drawable.sucursal4));

        return sucursales;
    }
}
