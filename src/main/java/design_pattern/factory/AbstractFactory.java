package design_pattern.factory;

public class AbstractFactory {

    interface CPU {
    }

    static class AmdCPU implements CPU {
    }

    static class IntelCPU implements CPU {
    }

    interface MainBoard {
    }

    static class AmdMainBoard implements MainBoard {
    }

    static class IntelMainBoard implements MainBoard {
    }

    interface Factory {
        CPU produceCPU();

        MainBoard produceMainBoard();
    }

    static class AmdFactory implements Factory {

        @Override
        public CPU produceCPU() {
            return new AmdCPU();
        }

        @Override
        public MainBoard produceMainBoard() {
            return new AmdMainBoard();
        }
    }

    static class IntelFactory implements Factory {

        @Override
        public CPU produceCPU() {
            return new IntelCPU();
        }

        @Override
        public MainBoard produceMainBoard() {
            return new IntelMainBoard();
        }
    }
}
