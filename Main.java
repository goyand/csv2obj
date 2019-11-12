// package java_env;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

public class Main {
  public static void main(String[] args) throws Exception {
    // コマンドライン引数の仕様
    Options options = new Options();
    options.addOption("h", "help", false, "print usage.");
    try {
      // コマンドラインの解析
      CommandLine cl = new DefaultParser().parse(options, args);

      if (cl.hasOption("help") || cl.getArgs().length == 0) {
        printUsage(options);
        return;
      }
      // System.out.println(cl.getArgs()[0]);
      // Path path = Paths.get("tengun.csv");
      // System.out.println(Paths.get("./tengun.csv"));
      // System.out.format("toString: %s%n", path.toString());
      // System.out.println(Paths.get(cl.getArgs()[0]));
  

      csv2obj(Paths.get(cl.getArgs()[0]), System.out);
    }
    catch (ParseException e) {
      printUsage(options);
    }
  }
  /**
  * CSVをobj形式に変換する
  * @param csvFile CSVファイルパス
  * @param os　出力先ストリーム
  * @throws IOException CSVファイルのオープンに失敗した
  */
  static void csv2obj(Path csvFile, PrintStream os) throws IOException {
    // ファイルを読み込む
    try (BufferedReader reader = Files.newBufferedReader(csvFile)) {
      String line, faceId = null;
      List<Long> vertex = new ArrayList<Long>();
      long vn = 0;
      while ((line = reader.readLine()) != null) {
        String[] columns = line.split(",");
        // 1面出力する
        // vertexには何かが入っていて、行のIDがfaceIDが異なる
        if (!columns[0].equals(faceId) && !vertex.isEmpty()){
          printFace ( os , vertex );
        }
          

        faceId = columns[0];
        // 1点出力する
        os.printf("v %.20f %.20f %.20f\n",
          Double.valueOf(StringUtils.remove(columns[1],'"')),
          Double.valueOf(StringUtils.remove(columns[2],'"')),
          Double.valueOf(StringUtils.remove(columns[3],'"'))
        );
        vertex.add(++vn);
        os.println(vertex);
      }
      
      // 最後の面を出力して完了
      printFace(os, vertex);
    }
  }

  protected static void printFace(PrintStream os, List<Long> vertex) {
    os.print("f ");
    vertex.forEach(v -> os.print(v + " "));
    os.println();

    // データに不正があったらコメント行にワーニングを出力する
    if (hasErrors(vertex)) {
      os.println("# warning");
    }

    vertex.clear();
  }
  /**
  * 面になっていない等の不整合を発見する
  * @param vertex
  * @return
  */
  static private boolean hasErrors(List<Long> vertex) {
    return false;
  }

  
  /**
  * 利用法の表示
  * @param options　コマンドラインの仕様
  */
  static void printUsage(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    System.out.println("mk3D (C) 2016-17 Tsutomu Watanuki");
    System.out.println("");
    formatter.printHelp("mk3d [options] <CSVファイル>", options);
    System.out.println("");
    System.out.println("例: mk3d <sample.csv> sample.obj");
  }
}


// // CLASSPATH動作確認用のプログラム
// import org.apache.commons.lang3.StringUtils;

// class Main {
// 	public static void main(String [] args) {
// 		System.out.println("Hello there");
// 		String x = "kat";
// 		String y = StringUtils.replace(x, "k", "c");
// 		System.out.println(y);
// 	}
// }