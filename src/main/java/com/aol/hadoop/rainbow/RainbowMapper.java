package com.aol.hadoop.rainbow;

import static com.aol.hadoop.rainbow.RainbowConstants.defaultAlgorithm;
import static com.aol.hadoop.rainbow.RainbowConstants.defaultHashDepth;
import java.io.IOException;
import org.apache.commons.codec.binary.Hex;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import fr.cryptohash.Digest;
import fr.cryptohash.MD2;
import fr.cryptohash.MD4;
import fr.cryptohash.MD5;
import fr.cryptohash.SHA1;
import fr.cryptohash.SHA224;
import fr.cryptohash.SHA256;
import fr.cryptohash.SHA384;
import fr.cryptohash.SHA512;
import fr.cryptohash.Whirlpool;


/**
 *
 */
public class RainbowMapper extends Mapper<BigIntegerWritable, Text, Text, Text> {

    private final Text oKey = new Text();
    private Digest digest;

    private int hashDepth;
    private String algorithm;
    private Boolean padded = false; // true for null padded strings e.g. NTLM

    @Override
    protected void setup(final Context context) throws IOException, InterruptedException {
        algorithm = context.getConfiguration().get("rainbow.algorithm", defaultAlgorithm);
        hashDepth = context.getConfiguration().getInt("rainbow.hashDepth", defaultHashDepth);

        switch(algorithm.toUpperCase()) {
            case "NTLM": digest = new MD4();
                padded = true;
                break;
            case "MD2": digest = new MD2();
                break;
            case "MD4": digest = new MD4();
                break;
            case "MD5": digest = new MD5();
                break;
            case "SHA1": digest = new SHA1();
                break;
            case "SHA224": digest = new SHA224();
                break;
            case "SHA256": digest = new SHA256();
                break;
            case "SHA384": digest = new SHA384();
                break;
            case "SHA512": digest = new SHA512();
                break;
            case "WHIRLPOOL": digest = new Whirlpool();
                break;
            default: throw new IOException("Unsupported algorithm: " + algorithm);
        }

    }

    @Override
    protected void map(final BigIntegerWritable iKey, final Text iVal, final Context context) throws IOException, InterruptedException {
        byte buffer[];
        if (padded) {
            buffer = iVal.toString().getBytes("UnicodeLittleUnmarked");
        } else {
            buffer = iVal.getBytes();
        }
        for(int c =0; c<hashDepth;c++) {
            buffer = digest.digest(buffer);
        }
//        oKey.set(b64.encode(buffer));
        oKey.set(Hex.encodeHexString(buffer));
        context.write(oKey, iVal);
    }
}
