const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
  optimization: {
    usedExports: true
  },
  entry: {
    examplePage: path.resolve(__dirname, 'src', 'pages', 'examplePage.js'),
    mainpage: path.resolve(__dirname, 'src', 'pages', 'mainpage.js'),
    portalpage: path.resolve(__dirname, 'src', 'pages', 'portalpage.js'),
    aboutpage: path.resolve(__dirname, 'src', 'pages', 'aboutpage.js'),
    contactpage: path.resolve(__dirname, 'src', 'pages', 'contactpage.js'),
    stockpage: path.resolve(__dirname, 'src', 'pages', 'stockpage.js'),
    checkoutpage: path.resolve(__dirname, 'src', 'pages', 'checkoutpage.js')
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].js',
  },
  devServer: {
    https: false,
    port: 8080,
    open: true,
    openPage: 'http://localhost:8080',
    // diableHostChecks, otherwise we get an error about headers and the page won't render
    disableHostCheck: true,
    contentBase: 'packaging_additional_published_artifacts',
    // overlay shows a full-screen overlay in the browser when there are compiler errors or warnings
    overlay: true,
    proxy: [
      {
        context: [
          '/example',
          '/contact',
          '/stocks',
          '/purchasedstocks',
        ],
        target: 'http://localhost:5001'
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/index.html',
      filename: 'index.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/portal.html',
      filename: 'portal.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/about.html',
      filename: 'about.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/contact.html',
      filename: 'contact.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/example.html',
      filename: 'example.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/checkout.html',
      filename: 'checkout.html',
      inject: false
    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/css'),
          to: path.resolve("dist/css"),
        }
      ]
    })
  ]
}
