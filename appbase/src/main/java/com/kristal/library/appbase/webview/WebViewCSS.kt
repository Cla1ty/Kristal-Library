package com.kristal.library.appbase.webview

/**
 * Created by Dwi on 8/10/2017.
 */

class WebViewCSS {
  private val buttonCss = "button {\n" +
      "  border-radius: 10px;\n" +
      "  color: #000000;\n" +
      "  background: #ffffff;\n" +
      "  padding: 5px 10px 5px 10px;\n" +
      "  border: solid #7cb342 2px;\n" +
      "  text-decoration: none;\n" +
      "}\n" +
      "button:hover {\n" +
      "  background: #f1f8e9;\n" +
      "  text-decoration: none;\n" +
      "}"
  
  private var body: String? = null
  var html: String? = null
    private set
  
  class Builder {
    private val css: WebViewCSS
    
    init {
      css = WebViewCSS()
    }
    
    fun setBody(body: String) {
      css.body = body
    }
    
    fun build(): WebViewCSS {
      css.html = "<html>\n" +
          "<head>\n" +
          "<style type=\"text/css\">\n" +
          //                    "@font-face {\n" +
          //                    "    font-family: MyFont;\n" +
          //                    "    src: url(\"file:///android_asset/fonts/MyFont.otf\")\n" +
          //                    "}\n" +
          "body {\n" +
          "color: #7cb342;\n" +
          //                    "    font-family: MyFont;\n" +
          //                    "    font-size: medium;\n" +
          //                    "    text-align: justify;\n" +
          "}\n" +
          css.buttonCss +
          "</style>\n" +
          "</head>\n" +
          "<body>\n" +
          css.body +
          "</body>\n" +
          "</html"
      
      return css
    }
  }
  
  class ButtonBuilder
}
