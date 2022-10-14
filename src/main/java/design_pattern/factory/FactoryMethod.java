package design_pattern.factory;

public class FactoryMethod {

    interface CPU {
    }

    static class AmdCPU implements CPU {
    }

    static class IntelCPU implements CPU {
    }

    interface CPUFactory {
        CPU produce();
    }

    static class AmdCPUFactory implements CPUFactory {
        @Override
        public CPU produce() {
            return new AmdCPU();
        }
    }

    static class IntelCPUFactory implements CPUFactory {
        @Override
        public CPU produce() {
            return new IntelCPU();
        }
    }
}


