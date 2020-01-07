package grantt;

public class Usuario {
	private String nombre;
	private String contraseña;
	
	private int id_equipo;
	private String nombreEquipo;
	
	private int presupuesto;
	
	public double valor;
	
	public Usuario(String nombre, String contraseña) {
		this.nombre = nombre;
		this.contraseña = contraseña;
	}
	
	public Usuario(String nombre, String contraseña, double valor) {
		this(nombre, contraseña);
		this.valor = valor;
	}
	
	public Usuario(String nombre, String nombreEquipo, int presupuesto) {
		this.nombre = nombre;
		this.nombreEquipo = nombreEquipo;
		this.presupuesto = presupuesto;
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
	
	public int getPresupuesto() {
		return this.presupuesto;
	}
	
	public String getNombreEquipo() {
		return this.nombreEquipo;
	}
}
