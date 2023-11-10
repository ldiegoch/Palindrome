# Applicación

Esta aplicación recibe como entrada un archivo xml con una lista de frases, las cuales probaremos si son Palindromos. El resultado de cada prueba aparecera registrado en un archivo de salida.

El archivo de entrada debe ser de la forma:

```
<?xml version="1.0"?>
  <dataset>
  <data1>Don't nod</data1>
  <data2>Dogma: I am God</data2>
  <data8>Was it Eliot's toilet I saw?</data8>
  <data3>Never odd or even</data3>
  <data10>How are you era who</data10>
  <data4>Too bad – I hid a boot</data4>
  <data5>Rats live on no evil star</data5>
  <data7>No trace; not one carton</data7>
  <data9>Murder for a jar of red rum</data9>
  <data11>Madam, I&amp;m Adam</data11>
  <test>This is not data</test>
</dataset>
```

En caso de ocurrir un error se generara un archivo errors.log con la informacion del error.

## Para ejecutar la aplicación

Esta es una aplicacion de linea de comandos creada con Java, para ejecutar requiere tener instalado el Java Run Time([Como instalarlo](https://www.java.com/en/download/help/download_options.html)), despúes ir a la console, navegar al path donde se encuentran ubicados los archivos de este proyecto y correr el siguiente comando:

```
java -jar Palindrome.jar
```

## Configuración

Esta applicacion cuenta con un archivo config.properties donde puede configurar el archivo xml que quiere usar como entrada y el nombre del archivo que quiere que guarde los resultados.

## Author

Esta aplicacion fue realizada: Luis Diego Cordero H.
