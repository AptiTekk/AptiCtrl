/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

var path = require('path');
var webpack = require('webpack');

var config = {
    cache: true,

    entry: {
        polyfills: path.join(__dirname, '../src/frontend/scripts/polyfills'),
        vendor: path.join(__dirname, '../src/frontend/scripts/vendors/vendors'),
        main: path.join(__dirname, '../src/frontend/scripts/main')
    },

    module: {
        loaders: [
            {
                test: /\.ts$/,
                loaders: ['awesome-typescript-loader', 'angular2-template-loader'],
                exclude: [/\.(spec|e2e)\.ts$/]
            },
            {
                test: /datatables\.net.*/,
                loader: 'imports-loader?define=>false'
            },
            {
                test: /\.json$/,
                loader: 'json-loader'
            },
            {
                test: /\.component\.html$/,
                loader: "to-string-loader!html-loader?-minimize"
            },
            {
                test: /\.html$/,
                loader: "html-loader?-minimize",
                exclude: [/\.component\.html$/]
            },
            {
                test: /\.component\.css$/,
                loader: "to-string-loader!css-loader"
            },
            {
                test: /\.css(\?v=[\d\.]+)?$/,
                loader: "style-loader!css-loader",
                exclude: [/\.component\.css$/]
            },
            {
                test: /\.(png|jpg|gif|svg)(\?v=[\d\.]+)?$/,
                loader: "file-loader?name=./packed/images/[hash].[ext]"
            },
            {
                test: /\.(ttf|eot|woff|woff2)(\?v=[\d\.]+)?$/,
                loader: 'file-loader?name=./packed/fonts/[hash].[ext]'
            }
        ]
    },

    plugins: [
        new webpack.optimize.CommonsChunkPlugin({
            name: ['polyfills', 'vendor', 'main'].reverse(),
            minChunks: Infinity
        }),
        new webpack.ProvidePlugin({
            jQuery: 'jquery',
            $: 'jquery',
            jquery: 'jquery'
        }),
        new webpack.OldWatchingPlugin()
    ],

    resolve: {
        extensions: ['', '.ts', '.js', '.json', '.jsx'],
        modulesDirectories: ['node_modules'],
        alias: {
            // Force all modules to use the same jquery version.
            'jquery': path.join(__dirname, '../node_modules/jquery/src/jquery')
        }
    },

    node: {
        global: true,
        process: true,
        Buffer: false,
        crypto: 'empty',
        module: false,
        clearImmediate: false,
        setImmediate: false,
        clearTimeout: true,
        setTimeout: true
    }
};

module.exports = config;
