package dev.acobano.springsecurity.basicauth.modelo.enumerados;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Rol
{
    USUARIO(Arrays.asList(Permiso.LEER_PRODUCTO)),
    ENCARGADO(Arrays.asList(Permiso.LEER_PRODUCTO, Permiso.ACTUALIZAR_PRODUCTO)),
    ADMINISTRADOR(Arrays.asList(Permiso.LEER_PRODUCTO, Permiso.GUARDAR_NUEVO_PRODUCTO, Permiso.ACTUALIZAR_PRODUCTO, Permiso.ELIMINAR_PRODUCTO));

    private List<Permiso> listaPermisos;
}
