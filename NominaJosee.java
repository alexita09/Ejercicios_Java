import java.util.Scanner;


public class NominaJosee {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Cuantos empleados desea registrar: ");
        int Tamano = sc.nextInt();
        sc.nextLine();

        String[] codigo = new String[Tamano];
        String[] nombre = new String[Tamano];
        String[] apellido = new String[Tamano];
        String[] puesto = new String[Tamano];

        double[] salario = new double[Tamano];
        double[] sfs = new double[Tamano];
        double[] afp = new double[Tamano];
        double[] isr = new double[Tamano];
        double[] prestamo = new double[Tamano];
        double[] neto = new double[Tamano];

        int opcion;

        do {
            System.out.println("\n===== MENU NOMINA KIKI LUVI========");
            System.out.println("1. Registrar empleados");
            System.out.println("2. Mostrar nomina");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:

                    for (int i = 0; i < Tamano; i++) {

                        System.out.println("\nEmpleado #" + (i + 1));

                        System.out.print("Nombre: ");
                        nombre[i] = sc.nextLine();

                        System.out.print("Apellido: ");
                        apellido[i] = sc.nextLine();

                        System.out.print("Puesto: ");
                        puesto[i] = sc.nextLine();

                        System.out.print("Salario: ");
                        salario[i] = sc.nextDouble();

                        System.out.print("Prestamo: ");
                        prestamo[i] = sc.nextDouble();
                        sc.nextLine();

                        
                        codigo[i] = (i + 1) + "" +
                                nombre[i].charAt(0) +
                                apellido[i].charAt(0);

                        
                        sfs[i] = salario[i] * 0.0304;
                        afp[i] = salario[i] * 0.0287;

                        double baseMensualISR= salario[i] - sfs[i] - afp[i];
                        double baseAnual = baseMensualISR * 12;

                        if (baseAnual <= 416220) {
                            isr[i] = 0;
                            isr[i] = ((baseAnual - 416220) * 0.15) / 12;
                        } else if (baseAnual <= 867123) {
                            isr[i] = (31216 + (baseAnual - 624329) * 0.20) / 12;
                        } else {
                            isr[i] = (79776 + (baseAnual - 867123) * 0.25) / 12;
                        }

                        neto[i] = salario[i] - sfs[i] - afp[i] - isr[i] - prestamo[i];
                    }

                    break;

                case 2:

                    System.out.println("\n================== NOMINA KIKI LOVI===========================");
                    System.out.printf("%-8s %-12s %-12s %-12s %-15s %-15s %-15s %-15s %-15s %-15s\n",
                            "Codigo", "Nombre", "Apellido", "Puesto",
                            "Salario", "SFS", "AFP", "ISR", "Prestamo", "Neto");

                    for (int i = 0; i < Tamano; i++) {

                        System.out.printf("%-8s %-12s %-12s %-12s %-15s %-15s %-15s %-15s %-15s %-15s\n",
                                codigo[i],
                                nombre[i],
                                apellido[i],
                                puesto[i],
                                "RD$" + String.format("%,.2f", salario[i]),
                                "RD$" + String.format("%,.2f", sfs[i]),
                                "RD$" + String.format("%,.2f", afp[i]),
                                "RD$" + String.format("%,.2f", isr[i]),
                                "RD$" + String.format("%,.2f", prestamo[i]),
                                "RD$" + String.format("%,.2f", neto[i]));
                    }

                    break;

                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 3);

       
    }
}