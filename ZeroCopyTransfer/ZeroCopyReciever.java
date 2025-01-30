package ZeroCopyTransfer;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ZeroCopyReciever {
    //to recieve data
    public static void main(String[] args) {
        int port = 9090;
        String output_file  = "recieved_file.log";
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        FileChannel fileChannel = FileChannel.open(Paths.get(output_file),
        StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            try (SocketChannel socketChannel = serverSocketChannel.accept()) {
                ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
                while (socketChannel.read(buffer) > 0) {
                    buffer.flip();
                    fileChannel.write(buffer);
                    buffer.clear();
                }
                System.out.println("File received successfully.");
            }
            System.out.println("File received successfully.");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
