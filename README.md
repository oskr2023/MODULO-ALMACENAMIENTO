**ALMACENAMIENTO DE NODOS**

Este proyecto de Java proporciona una implementación básica de un sistema de almacenamiento de nodos que se comunican entre sí a través de sockets TCP. El sistema incluye nodos con roles de líder y seguidor, así como un manejador de roles para asignar líderes y realizar operaciones de lectura y escritura.

**CLASES**
## Nodo
La clase **Nodo** representa un nodo en el sistema. Cada nodo puede tener un rol de líder o seguidor. Si un nodo es un seguidor, inicia un servidor TCP para aceptar conexiones entrantes de otros nodos. Los nodos se pueden unir como observadores para recibir mensajes. Además, un nodo puede escribir contenido y notificar a otros nodos sobre la escritura.
## ManejadorRoles
La clase **ManejadorRoles** gestiona la asignación de líderes y realiza operaciones de lectura y escritura en el sistema. Permite agregar nodos al sistema, asignar líderes y realizar operaciones de lectura y escritura.

**ROLES**
## Líder
Un nodo con el rol de líder puede escribir contenido y asignar líderes.
## Seguidor
Un nodo con el rol de seguidor inicia un servidor para aceptar conexiones y puede recibir mensajes del líder.

**OPERACIONES**
## Realizar Lectura
Un nodo realiza una operación de lectura para ver el contenido escrito por todos los nodos.
## Realizar Escritura
Solo el líder puede realizar operaciones de escritura. Cuando lo hace, notifica a los seguidores sobre la escritura.

## Comunicación en Red
Los nodos se comunican entre sí a través de sockets TCP. Cada nodo actúa como un servidor que acepta conexiones entrantes y como un cliente que se conecta a otros nodos.

**USO**

El programa proporciona un menú interactivo para realizar las siguientes operaciones:

1. **Agregar Nodo:** Agrega un nuevo nodo al sistema.
1. **Realizar Lectura:** Realiza una operación de lectura para ver el contenido escrito por todos los nodos.
1. **Realizar Escritura:** Realiza una operación de escritura si el nodo es el líder.
1. **Salir:** Sale del programa.
## Consideraciones
- Este código es un ejemplo básico y puede necesitar ajustes según los requisitos específicos.
- La comunicación en red está implementada utilizando sockets TCP.


**CLIENTE DE ALMACENAMIENTO**

Este programa Java representa el cliente de almacenamiento que permite interactuar con un sistema de almacenamiento de nodos. El sistema incluye nodos con roles de líder y seguidor que se comunican entre sí mediante sockets TCP. A continuación, se describen las características y la funcionalidad del programa:

**MENÚ DE OPCIONES**

1. **Establecer conexión a SERVIDOR:** Intenta establecer una conexión con el servidor del sistema de almacenamiento.
1. **Agregar Nodo:** Agrega un nuevo nodo al sistema de almacenamiento.
1. **Realizar Lectura:** Realiza una operación de lectura para ver el contenido escrito por todos los nodos.
1. **Realizar Escritura:** Realiza una operación de escritura si el nodo es el líder.
1. **Salir:** Sale del programa.

**FUNCIONALIDADES**

- **Establecer Conexión a SERVIDOR:** Intenta establecer una conexión con el servidor en el puerto 9999 de localhost. Si la conexión es exitosa, muestra un mensaje indicando que la conexión se ha establecido. Si hay un error, imprime un mensaje de error y regresa **false**.
- **Agregar Nodo:** Permite al usuario ingresar el nombre y el rol del nodo (líder o seguidor) y agrega un nuevo nodo al sistema de almacenamiento utilizando el **ManejadorRoles**.
- **Realizar Lectura:** Permite al usuario ingresar el nombre del nodo que realizará la operación de lectura. La operación de lectura muestra el contenido escrito por todos los nodos en el sistema utilizando el **ManejadorRoles**.
- **Realizar Escritura:** Permite al usuario ingresar el nombre del nodo que realizará la operación de escritura y el contenido a escribir. Solo el líder puede realizar operaciones de escritura, y se notifica a los seguidores sobre la escritura.

**NOTAS ADICIONALES**

- Si se intenta realizar alguna operación sin haber establecido una conexión con el servidor primero, se muestra un mensaje indicando que es necesario establecer la conexión antes de realizar esa operación.
- Si se ingresa una opción no válida en el menú, se muestra un mensaje indicando que la opción no es válida y se solicita al usuario que ingrese una opción válida.
- Se manejan las excepciones relacionadas con la entrada del usuario para evitar fallos inesperados.
- Se proporciona un mensaje de error específico si no se puede establecer la conexión con el servidor.
- El código está estructurado en métodos para facilitar la lectura y el mantenimiento del programa.

Este programa es un componente del sistema de almacenamiento y debe usarse junto con el servidor y otras clases del sistema para un funcionamiento completo.


