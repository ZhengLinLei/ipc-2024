/**
        MIT License

        Copyright (c) 2024 ZhengLinLei, Elena Clofent Muñoz and Alejandro Zafra Muñoz

        Permission is hereby granted, free of charge, to any person obtaining a copy
        of this software and associated documentation files (the "Software"), to deal
        in the Software without restriction, including without limitation the rights
        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
        copies of the Software, and to permit persons to whom the Software is
        furnished to do so, subject to the following conditions:

        The above copyright notice and this permission notice shall be included in all
        copies or substantial portions of the Software.

        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
        SOFTWARE.

 */
package models;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
	
	private final StringProperty nombre = new SimpleStringProperty();
	private final StringProperty apellidos = new SimpleStringProperty();
		
	public Persona(String nombre, String apellidos)
	{
		this.nombre.setValue(nombre);
		this.apellidos.setValue(apellidos);
	}
	
	public  StringProperty NombreProperty() {
		return this.nombre;
	}
	public String getNombre() {
		return this.NombreProperty().get();
	}
	public final void setNombre(String Nombre) {
		this.NombreProperty().set(Nombre);
	}
	public  StringProperty ApellidosProperty() {
		return this.apellidos;
	}
	public String getApellidos() {
		return this.ApellidosProperty().get();
	}
	public  void setApellidos(String Apellidos) {
		this.ApellidosProperty().set(Apellidos);
	}

}