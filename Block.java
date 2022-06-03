import java.io.Serializable;
import java.security.MessageDigest;

public class Block implements Serializable {
    String hash;

    public Block(String _prevHash, Transaction _transaction) {
        String prevHash = _prevHash;
        String time = Validator1.getDate();
        Transaction transaction = _transaction;
        hash = sha256(prevHash, time, transaction);
    }

    public String sha256(String prevHash, String time, Transaction transaction) {
        String dataToHash = prevHash + time + transaction;
        byte[] bytes = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes("UTF-8"));
        } catch (Exception e) {
            System.out.println(e);
        }
        StringBuffer hash = new StringBuffer();
        for (byte b : bytes) {
            hash.append(String.format("%02x", b));
        }
        System.out.println("\nHash of the new block: " + hash.toString());
        return hash.toString();
    }
}