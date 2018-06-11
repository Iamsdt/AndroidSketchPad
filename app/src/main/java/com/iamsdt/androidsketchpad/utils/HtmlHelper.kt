/*
 * Created by Shudipto Trafder
 * on 6/11/18 7:04 PM
 */

package com.iamsdt.androidsketchpad.utils

class HtmlHelper{
    companion object {
        fun getHtml(content: String) =
                """ <html>

<head>

    <style id='page-skin-1' type='text/css'>
        .container {
            width: auto;
            margin-left: 0px;
            margin-right: 0px;
            color: #444;
            font-size: 18px;
            font-weight: normal
        }

        pre {
            overflow: auto
        }

        code,
        pre,
        samp {
            font-family: monospace, monospace;
            font-size: 1em
        }

        #content {
            width: 100%;
            float: left;
            display: inline;
            margin-top: 10px
        }

        #main {}

        .post {
            color: #616161;
            margin-bottom: 5px;
            margin-top: 10px;
            margin-left: 0px;
            margin-right: 0px;
            position: initial
        }

        .main-content-body img {
            max-width: 100%;
            height: auto
        }

        .post-outer {
            margin-bottom: 10px
        }

        .post-title {
            color: #333333;
            padding: 0;
            margin: 0;
            font-size: 31px;
            line-height: 40px !important
        }

        .post .post-text {
            padding-bottom: 5px;
            clear: both
        }

        .post h2.post-title {
            font-weight: bold;
            letter-spacing: -1px;
            margin-top: 0px
        }

        .post h2 a {
            color: #353737;
            letter-spacing: -1px
        }

        .post ul.post-meta {
            color: #6b6969;
            clear: both;
            border-top: 1px solid #222020;
            border-bottom: 1px solid #222020;
            overflow: hidden;
            font-size: 12px;
            font-style: italic;
            padding: 5px 0px;
            list-style: none
        }

        .post ul.post-meta li {
            padding-right: 20px;
            float: left
        }

        .post ul.post-meta a {
            color: #0b6e69
        }

        .post h4,
        .post h5,
        .post h6 {
            color: #666
        }

        img.centered {
            display: block;
            margin: auto
        }

        img.alignright {
            display: inline;
            margin: 0px 0px 10px 20px
        }

        img.alignleft {
            display: inline;
            margin: 0px 0px 10px 0px
        }

        .alignleft {
            float: left
        }

        .alignright {
            float: right
        }

        .post blockquote {
            background: #FFFFFF !important;
            border-color: #2196F3 !important;
        }

    </style>
</head>

<body class='main-body'>
    <div class='container container-box'>
        <div class='row'>
            <div class='col s12 m12 l8'>
                <div class='main section' id='main'>
                    <div class='widget Blog' data-version='1' id='Blog1'>
                        <div class='main-content-body post'>
                             $content
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>

"""
    }
}