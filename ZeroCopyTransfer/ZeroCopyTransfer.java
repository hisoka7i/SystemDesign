package ZeroCopyTransfer;

import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ZeroCopyTransfer {
    public static void main(String[] args) {
        String filePath = "path here";
        String host = "ip here";
        int port = 9090;

        try (SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(host, port))) {
            FileChannel fileChannel = FileChannel.open(Paths.get(filePath), StandardOpenOption.READ);

            long fileSize = fileChannel.size();

            long transferred = fileChannel.transferTo(0, fileSize, socketChannel);
            System.out.println("Transferred " + transferred + " bytes from file to socket.");
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println("Transfer filed!");
        }
    }
}
