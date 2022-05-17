package usa.sesion1.adornostienda;

public class ImagenProducto {

    private int id;
    private String nombre;
    private int imagen;

    public ImagenProducto(int id, String nombre, int imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public ImagenProducto(String nombre, int imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}

