public class lab4 {
    public static class neuron {
        public double value;
        public double error;

        void funcact() {
            value = 1 / (1 + Math.exp(-value));
            //value = value;
        }
    }

    static class Network {
        public neuron[] neurons;
        double weights[][];

        Network() {
            neurons = new neuron[2];
            for (int i = 0; i<2; i++){
                neurons[i]= new neuron();
            }
            weights = new double[2][2];
        }

        void calculate(int p, double n, int x1, int x2, int d1, int d2) {
            for (int i = 0; i < p; i++) {
                neurons[i].value = x1 * weights[0][i] + x2 * weights[1][i];
                neurons[i].funcact();
            }
            for (int i = 0; i < n; i++) {
                neurons[i].value = x1 * weights[0][i] + x2 * weights[1][i];
                neurons[i].funcact();
            }
            neurons[0].error = (d1 - neurons[0].value);
            neurons[1].error = (d2 - neurons[1].value);
            for (int i = 0; i < 2; i++) {
                weights[0][i] = weights[0][i] + n * neurons[i].error * x1;
                weights[1][i] = weights[1][i] + n * neurons[i].error * x2;
            }
        }
    }

    public static void main(String[] args) {

        Network net = new Network();
        int num1[] = new int[] {0,1,1};
        int num2[] = new int[] {1,0,1};
        int d1[] = new int[] {1,0,1};
        int d2[] = new int[] {1,1,1};

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                net.weights[i][j] = Math.random() * (2) - 1; // -1 ... 1
                System.out.println(net.weights[i][j]);
            }
        }
        for (int i=0; i<10000; i++) {
            for (int j = 0; j < 3; j++){
                net.calculate(2,0.1, num1[j], num2[j],d1[j], d2[j]);
                if (i==9999) {
                    System.out.println("Y1 = " + String.format("%.5f",net.neurons[0].value));
                    System.out.println("Y2 = " + String.format("%.5f",net.neurons[1].value));
                }
            }

        }
    }
}