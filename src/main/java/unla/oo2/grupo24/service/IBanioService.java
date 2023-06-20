package unla.oo2.grupo24.Service;

import java.util.List;

import unla.oo2.grupo24.Entity.Banio;

public interface IBanioService {
	
	public List<Banio> listarTodos();
	
	public void guardarDispositivo(Banio b);
	
	public Banio buscarId(long id);
	
	public void eliminar(long id);

}
