import java.util.Scanner;




public class Complejo01s{

    
    public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);

        int[][][] habitantes = new int[7][20][6];
        int opcion;

        do {

      System.out.println("\n==== Residencial skipiridipi ==== ");
      System.out.println("1. Registrar habitantes");
      System.out.println("2. Ver total del complejo");
      System.out.println("3. Ver apartamentos vacios");
      System.out.println("4. Salir");
      System.out.print("Seleccione una opcion: ");

       opcion = scanner.nextInt();

      switch (opcion) {

       case 1:

       System.out.print("¿Cuantos apartamentos desea registrar?: ");
        int cantidad = scanner.nextInt();

         for (int i = 0; i < cantidad; i++) {

          System.out.println("\nRegistro #" + (i + 1));

          System.out.print("Torre (1-7): ");
         int torre = scanner.nextInt() - 1;

           System.out.print("Piso (1-20): ");
          int piso = scanner.nextInt() - 1;

              System.out.print("Apartamento (1-6): ");
             int apto = scanner.nextInt() - 1;

            System.out.print("Habitantes: ");
           habitantes[torre][piso][apto] = scanner.nextInt();
                    }

            System.out.println("Datos registrados correctamente.");
            break;

           case 2:

          int total = 0;

         for (int torre = 0; torre < 7; torre++) {
          for (int piso = 0; piso < 20; piso++) {
             for (int apto = 0; apto < 6; apto++) {
            total += habitantes[torre][piso][apto];
                            }
                        }
                    }

          System.out.println("Su total de habitantes del complejo es: " + total);
             break;

        case 3:

    System.out.println("Apartamentos vacíos:");

       for (int torre = 0; torre < 7; torre++) {
       for (int piso = 0; piso < 20; piso++) {
        for (int apto = 0; apto < 6; apto++) {

        if (habitantes[torre][piso][apto] == 0) {

          System.out.println("Torre " + (torre + 1) +
             " Piso " + (piso + 1) +
             " Apto " + (apto + 1));
                        }
                         }
                        }
                       }

       break;

    case 4:
       System.out.println("Saliendo del sistema");
        break;
            }

   } while (opcion != 4);

     scanner.close();
    }
}


   

       