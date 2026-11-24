    package com.Evaluador.springboot_app.model;
    import jakarta.persistence.*;

    @Entity
    @Table(name = "usuarios")
    public class Usuario {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String image;

        @Column(name = "correo", nullable = false, unique = true, length = 50)
        private String correo;

        @Column(nullable = false, length = 255)
        private String contrasenia;

        private String rol;

        public Usuario() {}

        public Usuario(Long id, String image, String correo, String contrasenia, String rol) {
            this.id = id;
            this.image = image;
            this.correo = correo;
            this.contrasenia = contrasenia;
            this.rol = rol;
        }

        public Long getId() {
            return id;
        }

        public String getImage() {
            return image;
        }

        public String getCorreo() {
            return correo;
        }

        public String getContrasenia() {
            return contrasenia;
        }

        public String getRol() {
            return rol;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public void setContrasenia(String contrasenia) {
            this.contrasenia = contrasenia;
        }

        public void setRol(String rol) {
            this.rol = rol;
        }

        @Override
        public String toString() {
            return "Usuario{id=" + id + ", rol=" + rol + "}";
        }
    }
