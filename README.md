# callCenter

Silvina Luque
Ejercicio ingreso JAVA

COMENTARIOS SOBRE LOS PUNTOS EXTRAS:

---“Dar alguna solución sobre qué pasa con una llamada cuando no hay ningún empleado libre.”
El dispatcher recepciona las llamadas y las va insertando en una cola que permite el manejo concurrente.  
Mientras haya llamadas en la cola, el dispatcher se va a encargar de chequear si hay empleados disponibles para asignárselas. 
En el caso de encontrar un empleado disponible, se asigna la primer llamada de la cola, ésta se extrae de la cola y el empleado 
pasa a estar en estado ocupado hasta que finalice la misma.
Mientras que no haya empleados libres las llamadas se mantienen en la cola. 
Lo cual podría interpretarse como que quedan en “espera”. 
Se podría implementar como mejora que haya un tiempo máximo de espera y que luego  la llamada se derive a un contestador  
automático donde se registre el mensaje y numero. Luego con posterioridad un agente disponible puede chequear una cola de 
mensajes de llamadas no atendidas.

--“Dar alguna solución sobre qué pasa con una llamada cuando entran más de 10 llamadas concurrentes.”
Cuando ingresan más de diez llamadas concurrentemente no se generan conflictos.  
Las llamadas se van encolando en una ConcurrentLinkedDeque, esta implementación permite trabajar de forma segura e eficiente
 en lo que respecta a acceso concurrente por subprocesos, las llamadas quedan a la espera de que  el ExecutorService  las 
 asigne a algún “empleadoThread” disponible.
