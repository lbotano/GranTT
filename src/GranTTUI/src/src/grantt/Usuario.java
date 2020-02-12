package grantt;

public class Usuario {
	private String nombre;
	private String contraseña;
	
	private int id_equipo;
	private String nombreEquipo;
	
	public double valor;
	
	public Usuario(String nombre, String contraseña) {
		this.nombre = nombre;
		this.contraseña = contraseña;
	}
	
	public Usuario(String nombre, double valor) {
		this.nombre = nombre;
		this.valor = valor;
	}
	
	public void setEquipo(int id_equipo) {
		this.id_equipo = id_equipo;
	}
	
	public int getEquipo() {
		return this.id_equipo;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getContraseña() {
		return this.contraseña;
	}
	
	public double getPresupuesto() {
		return BaseDeDatos.obtenerValorEquipoUsuario(this.getNombre());
	}
	
	public String getNombreEquipo() {
		return this.nombreEquipo;
	}
}
