package entidades;

import java.io.Serializable;

public class IdClave implements Serializable, Comparable<IdClave> {
	
	private String idioma;
	private String silaba;
	private int secuencia;
		

	public IdClave() {
		super();
	}
	
	public IdClave(String idioma, String silaba, int secuencia) {
		super();
		this.idioma = idioma;
		this.silaba = silaba;
		this.secuencia = secuencia;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public String getSilaba() {
		return silaba;
	}
	public void setSilaba(String silaba) {
		this.silaba = silaba;
	}
	public int getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(int secuencia) {
		this.secuencia = secuencia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idioma == null) ? 0 : idioma.hashCode());
		result = prime * result + secuencia;
		result = prime * result + ((silaba == null) ? 0 : silaba.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdClave other = (IdClave) obj;
		if (idioma == null) {
			if (other.idioma != null)
				return false;
		} else if (!idioma.equals(other.idioma))
			return false;
		if (secuencia != other.secuencia)
			return false;
		if (silaba == null) {
			if (other.silaba != null)
				return false;
		} else if (!silaba.equals(other.silaba))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IdClave [idioma=" + idioma + ", silaba=" + silaba + ", secuencia=" + secuencia + "]";
	}

	@Override
	//se devuelve el numero cero cuando el objeto actual es igual a el objeto argumento de compareTo().
	//se devuelve un numero menor que cero cuando el objeto actual es menor que el arumento de compareTo().
	//se devuelve un numero mayor que cero cuando el objeto actual es maYor que el arumento de compareTo().
	public int compareTo(IdClave idc) {
		if (idc == null)
			return 1;
		if (this == idc )
			return 0;

		int res = this.idioma.compareTo(idc.idioma); 
		if (res == 0) {
			res = this.silaba.compareTo(idc.silaba);
			if (res == 0) {
				res = this.secuencia - idc.secuencia;
			}
		}
			
		return res;
	}


}