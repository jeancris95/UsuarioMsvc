package org.jean.springcloud.msvc.usuarios.ServicesImpl;

import org.jean.springcloud.msvc.usuarios.models.entity.Usuario;
import org.jean.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.jean.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return (List<Usuario>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porId(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional()
    public Usuario guardar(Usuario user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> porEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
