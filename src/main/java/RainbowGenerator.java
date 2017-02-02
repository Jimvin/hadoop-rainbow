
import com.aol.hadoop.rainbow.RainbowInputFormat;
import com.aol.hadoop.rainbow.RainbowMapper;
import com.aol.hadoop.rainbow.RainbowReducer;
import org.apache.commons.cli.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import static com.aol.hadoop.rainbow.RainbowConstants.*;

/**
 * RainbowGenerator
 */
public class RainbowGenerator extends Configured implements Tool {

    public static Job createJob(final Configuration conf, final Path outputPath) throws Exception {
        final Job job = Job.getInstance(conf, "Rainbow Table Generator");
        job.setJarByClass(RainbowGenerator.class);

        // Configure Input
        job.setInputFormatClass(RainbowInputFormat.class);

        // Configure Mapper
        job.setMapperClass(RainbowMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        // Configure Reducer
        job.setReducerClass(RainbowReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setNumReduceTasks(conf.getInt("rainbow.reducers", defaultReducers));

        // Configure Output
        SequenceFileOutputFormat.setOutputPath(job, outputPath);
        SequenceFileOutputFormat.setCompressOutput(job, true);
        SequenceFileOutputFormat.setOutputCompressionType(job, SequenceFile.CompressionType.BLOCK);
        SequenceFileOutputFormat.setOutputCompressorClass(job, BZip2Codec.class);

        System.out.printf("Charset  : (%d) %s\n",
                job.getConfiguration().get("rainbow.charset").length(),
                job.getConfiguration().get("rainbow.charset"));
        System.out.printf("Lengths  : %d - %d\n",
                Integer.parseInt(job.getConfiguration().get("rainbow.minLength")),
                Integer.parseInt(job.getConfiguration().get("rainbow.maxLength")));
        System.out.printf("Algorithm: %s\n", job.getConfiguration().get("rainbow.algorithm"));
        System.out.printf("Mappers  : %s\n", job.getConfiguration().get("rainbow.mappers"));
        System.out.printf("Reducers : %s\n", job.getConfiguration().get("rainbow.reducers"));
        System.out.printf("Passwords: %s\n", RainbowInputFormat.permutations(
                job.getConfiguration().get("rainbow.charset"),
                Integer.parseInt(job.getConfiguration().get("rainbow.minLength")),
                Integer.parseInt(job.getConfiguration().get("rainbow.maxLength"))
        ));
        job.submit();
        System.out.printf("Tracking : %s\n", job.getTrackingURL());
        return job;
    }

    @Override
    public int run(final String[] args) throws Exception {
        Configuration conf = getConf();

        // Set Defaults
        conf.set("rainbow.algorithm", defaultAlgorithm);
        conf.set("rainbow.charset", defaultCharset);
        conf.setInt("rainbow.minLength", defaultMinLength);
        conf.setInt("rainbow.maxLength", defaultMaxLength);
        conf.setInt("rainbow.mappers", defaultMappers);
        conf.setInt("rainbow.reducers", defaultReducers);

        // Parse Options
        final Options options = new Options();
        options.addOption("a", "rainbow.algorithm", true, "Digest Algorithm");
        options.addOption("c", "rainbow.charset", true, "Password Characterset");
        options.addOption("b", "rainbow.minLength", true, "Minimum password Length");
        options.addOption("e", "rainbow.maxLength", true, "Maximum password Length");
        options.addOption("h", "help", false, "Displays help");
        options.addOption("m", "rainbow.mappers", true, "Mapper Count");
        options.addOption("r", "rainbow.reducers", true, "Reducer Count");
        final GenericOptionsParser gop = new GenericOptionsParser(conf, options, args);

        // Process command line options
        CommandLine cmd = gop.getCommandLine();
        if (cmd.hasOption("h")) {
            help(options);
        }
        if (cmd.hasOption("a")) {
            conf.set("rainbow.algorithm", cmd.getOptionValue("a"));
        }
        if (cmd.hasOption("c")) {
            conf.set("rainbow.charset", cmd.getOptionValue("c"));
        }
        if (cmd.hasOption("b")) {
            conf.set("rainbow.minLength", cmd.getOptionValue("b"));
        }
        if (cmd.hasOption("e")) {
            conf.set("rainbow.maxLength", cmd.getOptionValue("e"));
        }
        if (cmd.hasOption("m")) {
            conf.set("rainbow.mappers", cmd.getOptionValue("m"));
        }
        if (cmd.hasOption("r")) {
            conf.set("rainbow.reducers", cmd.getOptionValue("r"));
        }

        final Job job = createJob(gop.getConfiguration(), new Path(gop.getRemainingArgs()[0]));
        while (!job.isComplete()) {
            System.out.printf("Map: %7.3f%% Reduce: %7.3f%%\n",
                    job.mapProgress() * 100, job.reduceProgress() * 100);
            Thread.sleep(10000);
        }
        return 0;
    }
    public void help(Options options) {
        HelpFormatter help = new HelpFormatter();
        help.printHelp("RainbowGenerator <args> <output_directory>", options);
        System.exit(1);
    }
    public static void main(final String[] args) throws Exception {
        System.exit(ToolRunner.run(new RainbowGenerator(), args));
    }
}
