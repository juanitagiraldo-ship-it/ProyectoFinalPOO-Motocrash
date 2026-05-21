# ProyectoFinalPOO-Motocrash
El objetivo del juego es conducir una motocicleta, esquivar obstáculos en la carretera, recolectar potenciadores (PowerUps) y acumular la mayor puntuación posible antes de perder todas las vidas.

## Arquitectura del Proyecto (MVC)
El proyecto está organizado en tres paquetes principales para separar la lógica de negocio de la interfaz gráfica:

### 1. controller (Controladores)
Se encarga de gestionar el flujo del juego, los hilos de ejecución y las entradas del usuario.

Main: Punto de entrada edl juego. 
GameController: El motor del juego. Implementa 'Runnable' para manejar el bucle principal, actualizar la lógica, mover entidades y verificar colisiones.
KeyboardController: Captura las entradas del teclado 'KeyAdapter' para la moto. 
SoundManager: Controla la repoducción de efectos de sonido y música de fondo. 

### 2. model (Modelos)
Contiene las entidades del juego, sus atributos y su comportamiento interno.
Entity (Abstracta): Clase base con coordenadas (x, y), dimensiones, sprite y la firma del método 'update()'.
Moto: El jugador. Registra vidas, puntuación, estado de escudos o turbo, y sus métodos de movimiento.
Obstacle: Elementos que el jugador debe esquivar. 
PowerUp: Objetos coleccionables (Escudo, Turbo).
Road: Maneja el desplazamiento infinito del fondo para simular movimiento.

### 3. view (Vistas)
Gestiona la interfaz gráfica de usuario (GUI) mediante componentes de Java Swing.
MainWindow: La ventana principal ('JFrame') que utiliza 'CardLayout' para intercambiar las pantallas del juego. 
GamePanel: El lienzo principal ('JPanel') deonde se renderiza el juego, los personajes y el HUD (vidas, tiempo). 
NamePanel, PanelGameOver: Pantallas secundarias para los menús y el estado de fin de juego.

## Características Principales
Bucle de Juego Eficiente: Control de lógica y colisiones en tiempo real mediante un hilo ('Thread') independiente.
Sistema de Power-Ups: Mecánicas de escudo ('shielded') y velocidad turbo ('turboActivo').
Fondo Infinito: Movimiento vertical u horizontal continuo de la carretera adaptativo a la velocidad.
Gestión de Estados: Transición limpia entre pantallas (Menú, Instrucciones, Juego, Fin de partida) usando 'CardLayout'.
Audio Integrado: Soporte para música en bucle y efectos de sonido simultáneos.