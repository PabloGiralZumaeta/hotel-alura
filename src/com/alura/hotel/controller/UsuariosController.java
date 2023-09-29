package com.alura.hotel.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.alura.hotel.dao.UsuariosDAO;
import com.alura.hotel.factory.Factory;
import com.alura.hotel.modelo.Usuarios;

import views.Login;
import views.MenuPrincipal;
import views.MenuUsuario;

public class UsuariosController implements ActionListener {

	private UsuariosDAO usuarioDao;
	private Login vista;

	public UsuariosController(Login vista) {
		this.vista = vista;
		var factory = new Factory();
		this.usuarioDao = new UsuariosDAO(factory.recuperarConnection());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String nombre = vista.getNombre();
		String contraseña = vista.getContraseña();
		
			if(usuarioDao.validarUsuario(new Usuarios(nombre, contraseña))) {
			MenuUsuario menu = new MenuUsuario();
			menu.setVisible(true);
			vista.dispose();
		}else {
			JOptionPane.showMessageDialog(vista,"Usuario o contraseña incorrecta");
		}
		

	}

}
