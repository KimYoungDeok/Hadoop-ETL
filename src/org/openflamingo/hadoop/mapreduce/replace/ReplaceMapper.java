package org.openflamingo.hadoop.mapreduce.replace;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.openflamingo.hadoop.etl.replace.ReplaceCriteria;
import org.openflamingo.hadoop.etl.utils.Row;

import java.io.IOException;
import java.util.Arrays;

/**
 * Description.
 *
 * @author Youngdeok Kim
 * @since 1.0
 */
public class ReplaceMapper extends Mapper<LongWritable, Text, NullWritable, Text> {

	private ReplaceCriteria replaceCriteria;
	private String delimeter;

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		Configuration configuration = context.getConfiguration();
		delimeter = configuration.get("delimeter");
		String replace = configuration.get("replace");

		replaceCriteria = new ReplaceCriteria();
		replaceCriteria.parseReplaceCommand(replace, delimeter);

		super.setup(context);
	}

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] columns = Row.parseByDelimeter(value.toString(), delimeter);
		replaceCriteria.doReplace(columns);

		String result = Row.arrayToString(columns, delimeter);

		context.write(NullWritable.get(), new Text(result));
	}
}
