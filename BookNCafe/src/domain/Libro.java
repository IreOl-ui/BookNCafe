package domain;

public class Libro {
	
	    private String isbn;
	    private String titulo;
	    private String autor;
	    
		public Libro(String isbn, String titulo, String autor) {
			
			this.isbn = isbn;
			this.titulo = titulo;
			this.autor = autor;
		}
		
		
		public Libro() {
			
		}


		public String getIsbn() {
			return isbn;
		}
		public void setIsbn(String isbn) {
			this.isbn = isbn;
		}
		public String getTitulo() {
			return titulo;
		}
		//setters no necesarios,ya que los libros no cambian de valor
		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
		public String getAutor() {
			return autor;
		}
		public void setAutor(String autor) {
			this.autor = autor;
		}


		@Override
		public String toString() {
			return "Libro [isbn=" + isbn + ", titulo=" + titulo + ", autor=" + autor + "]";
		}

		@Override
		public int hashCode() {
		    return java.util.Objects.hash(isbn);
		}

		@Override
		public boolean equals(Object obj) {
		    if (this == obj) return true;
		    if (obj == null || getClass() != obj.getClass()) return false;
		    Libro other = (Libro) obj;
		    return java.util.Objects.equals(isbn, other.isbn);
		}

}
