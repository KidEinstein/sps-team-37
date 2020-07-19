package com.google.sps.data;

public final class EmojiSelection {

  private static String[] emoji1 = {"😾","😡","😠","😧","💔","💩"};
  private static String[] emoji2 = {"😱","🙀","😭","😟","👎","😰","😖","😕","😢","😿","🤕","🤢","😥"};
  private static String[] emoji3 = {"😞","😨","😳","😬","🤥","😮","😣","💀","🤧","😫","😒","😩","😔"};
  private static String[] emoji4 = {"😵","🤒","👊","😦","👻","😯","😷","🙄","🙁","😓","🤔","🤐","🤡"};
  private static String[] emoji5 = {"🤤","😑","😐","😶","😴","😪","😝","😤","🙃","🤝","😉","😆","🙏"};
  private static String[] emoji6 = {"👍","😲","😊","🤞","😁","😀","🤗","😗","😽","😚","😙","🙂","🤑"};
  private static String[] emoji7 = {"😎","👌","😌","😄","😸","😃","😺","😏","😼","😅","✌️","😛","🤠"};
  private static String[] emoji8 = {"💋","👄","💜","🖤","💙","👏","💘","💝","💚","😍","😻"};
  private static String[] emoji9 = {"💓","💗","😇","😂","😹","😘","😋","💛","🤣"};
  private static String[] emoji10 = {"💯","💞","💖","💕","🙌"};

  // Pick a random emoji based on the sentiment score (1-10)
  public static String getEmoji(int sentimentScore) {
    String emoji = "❓";
    switch (sentimentScore) {
      case 1:
        emoji = emoji1[(int)(Math.random() * emoji1.length)];
        break;
      case 2:
        emoji = emoji2[(int)(Math.random() * emoji2.length)];
        break;
      case 3:
        emoji = emoji3[(int)(Math.random() * emoji3.length)];
        break;
      case 4:
        emoji = emoji4[(int)(Math.random() * emoji4.length)];
        break;
      case 5:
        emoji = emoji5[(int)(Math.random() * emoji5.length)];
        break;
      case 6:
        emoji = emoji6[(int)(Math.random() * emoji6.length)];
        break;
      case 7:
        emoji = emoji7[(int)(Math.random() * emoji7.length)];
        break;
      case 8:
        emoji = emoji8[(int)(Math.random() * emoji8.length)];
        break;
      case 9:
        emoji = emoji9[(int)(Math.random() * emoji9.length)];
        break;
      case 10:
        emoji = emoji10[(int)(Math.random() * emoji10.length)];
        break;
    }
    return emoji;
  }
}