public class lab3 {
    public static class neuron {
        public double value;
        public double error;

        void funcact() {
            value = 1 / (1 + Math.exp(-value));
            //value = (Math.exp(2*value)-1)/(Math.exp(2*value)+1);
            //value = 0.6 * value;
            //value = value;
        }
    }

    static class Network {
        public neuron[] neurons1;
        public neuron[] neurons2;
        double weights1[][];
        double weights2[];

        Network() {
            neurons1 = new neuron[2];
            for (int i = 0; i<2; i++){
                neurons1[i]= new neuron();
            }
            neurons2 = new neuron[1];
            neurons2[0]= new neuron();

            weights1 = new double[2][2];
            weights2 = new double[2];
        }

        void randWeights(int n) {
            for (int i = 0; i < n; i++) {
                weights2[i] = Math.random() * (2) - 1;
                for (int j = 0; j < n; j++) {
                    weights1[i][j] = Math.random() * (2) - 1;
                    System.out.println("weights = " + weights1[i][j]);
                }
            }
        }

        void predict(int n, int x1, int x2) {
            for (int i = 0; i < n; i++) {
                neurons1[i].value = x1 * weights1[0][i] + x2 * weights1[1][i];
                neurons1[i].funcact();
            }
            neurons2[0].value = neurons1[0].value * weights2[0] + neurons1[1].value * weights2[1];
            neurons2[0].funcact();
        }

        void changeWeights(double n, int x1, int x2, int d){

            double sig1;
            double sig2[] = new double[2];

            sig1 = (d - neurons2[0].value) * neurons2[0].value * (1 - neurons2[0].value);
            weights2[0] = weights2[0] + n * sig1 * neurons1[0].value;
            weights2[1] = weights2[1] + n * sig1 * neurons1[1].value;

            for (int i = 0; i < 2; i++)
            {
                sig2[i] = neurons1[i].value * (1 - neurons1[i].value) * sig1 * weights2[i];
            }

            for (int i = 0; i < 2; i++)
            {
                weights1[0][i] = weights1[0][i] + n * sig2[0] * x1;
                weights1[1][i] = weights1[1][i] + n * sig2[1] * x2;
            }
        }
    }

    public static void main(String[] args) {

        Network net = new Network();
        int num1[] = new int[] {0,1,1};
        int num2[] = new int[] {1,0,1};
        int d[] = new int[] {0,0,0};


        net.randWeights(2);
        int p=0;
        for (int i=0; i<1000000; i++) {
            for (int j = 0; j < 3; j++){
                net.predict(2, num1[j], num2[j]);
                net.changeWeights(0.1, num1[j], num2[j], d[j]);
                if (i>=999999 || i ==0) {
                    // System.out.println("Erorr = " + String.format("%.5f",1-net.neurons2[0].value));
                    if (i==999999) {
                        System.out.println("Y"+ ++p +"= " + String.format("%.5f",net.neurons2[0].value));
                    }
                }
            }
        }
    }
}