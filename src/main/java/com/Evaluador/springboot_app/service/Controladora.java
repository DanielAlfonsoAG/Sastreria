package com.Evaluador.springboot_app.service;

import com.Evaluador.springboot_app.model.*;
import com.Evaluador.springboot_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Controladora {

    private final UsuarioRepository usuarioRepo;
    private final DatosRepository DatosRepo;
    private final EmpleadoRepository empleRepo;
    private final ClienteRepository clientRepo;
    private final DisfrazRepository disfraRepo;
    private final VestidoDamaRepository vestiRepo;
    private final TrajeCaballeroRepository trajeCaRepo;
    private final PasswordEncoder passwordEncoder;
    private final PrendasvendidasRepository prendasVenRepo;
    private final PrendaRepository prendaRepo;

    @Autowired
    public Controladora(UsuarioRepository usuarioRepo,PrendaRepository prendaRepo, PrendasvendidasRepository prendasVenRepo, DatosRepository datosRepo, EmpleadoRepository empleRepo, ClienteRepository clientRepo, DisfrazRepository disfraRepo, VestidoDamaRepository vestiRepo, TrajeCaballeroRepository trajeCaRepo, PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.DatosRepo = datosRepo;
        this.empleRepo = empleRepo;
        this.clientRepo = clientRepo;
        this.disfraRepo = disfraRepo;
        this.vestiRepo = vestiRepo;
        this.trajeCaRepo = trajeCaRepo;
        this.passwordEncoder = passwordEncoder;
        this.prendasVenRepo = prendasVenRepo;
        this.prendaRepo = prendaRepo;
    }

    public void registrar(Usuario usuario) {
        String contraseniaEncriptada = passwordEncoder.encode(usuario.getContrasenia());
        usuario.setContrasenia(contraseniaEncriptada);
        usuarioRepo.save(usuario);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepo.findByCorreo(correo).orElse(null);
    }

    public Datos buscarRuta(String ruta) {
        return DatosRepo.findByRuta(ruta).orElse(null);
    }

    public boolean existeUsuario(String correo) {
        return usuarioRepo.findByCorreo(correo).isPresent();
    }

    public Optional<Usuario> buscarPorCorreoOptional(String correo) {
        return usuarioRepo.findByCorreo(correo);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepo.findAll();
    }

    public void borrarUsuario(long id) {
        usuarioRepo.deleteById(id);
    }

    public Optional<Usuario> traerUsuario(long id) {
    Optional<Usuario> usu = usuarioRepo.findById(id);
        return usu;
    }
    public Usuario traerunUsuario(long id) {
        Optional<Usuario> usu = usuarioRepo.findById(id);
        return usu.orElse(null);
    }

    public void editarUsuario(Usuario usu) {
        String contraseniaEncriptada = passwordEncoder.encode(usu.getContrasenia());
        usu.setContrasenia(contraseniaEncriptada);
        usuarioRepo.save(usu);
    }

    public Optional<Datos> traerDatos(long id) {
        return DatosRepo.findById(id);
    }

    public List<Usuario> findAllById(List<Long> ids) {
        return usuarioRepo.findAllById(ids);
    }

    public List<Empleado> listarEmpleados() {
        return empleRepo.findAll();
    }

    public void borrarEmpleado(long idEmpleado) {
        empleRepo.deleteById(idEmpleado);
    }

    public Optional<Empleado> traerEmpleado(long idEmpleado) {
        return empleRepo.findById(idEmpleado);
    }

    public void editarEmpleado(Empleado emp) {
        empleRepo.save(emp);
    }

    public void crearEmpleado(Empleado emp) {
        empleRepo.save(emp);
    }

    public List<Cliente> listarClientes() {
        return clientRepo.findAll();
    }

    public void borrarCliente(long idClientes) {
        clientRepo.deleteById(idClientes);
    }

    public Optional<Cliente> traerCliente(long idCliente) {
        return clientRepo.findById(idCliente);
    }

    public void editarCliente(Cliente cli) {
        clientRepo.save(cli);
    }
    
    public void crearCliente(Cliente cli) {
        clientRepo.save(cli);
    }

    public void crearDisfraz(Disfraz dis) {
        disfraRepo.save(dis);
    }

    public List<Disfraz> listarDisfraces() {
        return disfraRepo.findAll();
    }

    public void borrarDisfraz(long idDisfraz) {
        disfraRepo.deleteById(idDisfraz);
    }

    public Optional<Disfraz> traerDisfraz(long idDisfraz) {
        return disfraRepo.findById(idDisfraz);
    }

    public void editarDisfraz(Disfraz dis) {
        disfraRepo.save(dis);
    }

    public List<TrajeCaballero> listarTrajes() {
        return trajeCaRepo.findAll();
    }

    public void borrarTrajeCaballero(long idTrajeCaballero) {
        trajeCaRepo.deleteById(idTrajeCaballero);
    }

    public Optional<TrajeCaballero> traerTrajeCaballero(long idTrajeCaballero) {
        return trajeCaRepo.findById(idTrajeCaballero);
    }

    public void editarTrajeCaballero(TrajeCaballero trajeCa) {
        trajeCaRepo.save(trajeCa);
    }

    public void crearTrajeCaballero(TrajeCaballero trajeCa) {
        trajeCaRepo.save(trajeCa);
    }

    public List<VestidoDama> listarVestidoDama() {
        return vestiRepo.findAll();
    }

    public void borrarVestidoDama(long idVestidoDama) {
        vestiRepo.deleteById(idVestidoDama);
    }

    public Optional<VestidoDama> traerVestidoDama(long idVestidoDama) {
        return vestiRepo.findById(idVestidoDama);
    }

    public void editarVestidoDama(VestidoDama vestidoDama) {
        vestiRepo.save(vestidoDama);
    }

    public void crearVestidoDama(VestidoDama vestidoDama) {
        vestiRepo.save(vestidoDama);
    }

    public List<Prenda> listarPrendas() {
        return prendaRepo.findAll();
    }

    public void guardarPrendaVendida(Prendasvendidas prendasvendidas) {
        prendasVenRepo.save(prendasvendidas);
    }

    public Prenda buscarDisfrazPorId(Long disfrazId) {
        return prendaRepo.findById(disfrazId)
                .orElseThrow(() -> new RuntimeException("Disfraz no encontrado"));
    }

    public Prenda buscarTrajePorId(Long trajeId) {
        return prendaRepo.findById(trajeId)
                .orElseThrow(() -> new RuntimeException("Traje no encontrado"));
    }

    public Prenda buscarVestidoPorId(Long vestidoId) {
        return prendaRepo.findById(vestidoId)
                .orElseThrow(() -> new RuntimeException("Vestido no encontrado"));
    }

    public Cliente buscarClientePorId(Long clienteId) {
        return clientRepo.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Vestido no encontrado"));
    }

    public List<Prendasvendidas> listarVentas() {
        return prendasVenRepo.findAll();
    }

    public void borrarVenta(long idVenta) {
        prendasVenRepo.deleteById(idVenta);
    }

    public Optional<Prendasvendidas> traerVentas(long id) {
        return prendasVenRepo.findById(id);
    }

    public void editarVentas(Prendasvendidas prendasvendidas) {
        prendasVenRepo.save(prendasvendidas);
    }
}



