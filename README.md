# playing-with-fundamental-cljs

## Usage

    # npm install -g shadow-cljs
    
    $ git clone https://github.com/rotaliator/playing-with-fundamental-cljs.git
    $ cd playing-with-fundamental-cljs
    $ npm install
    $ ln -s ../../node_modules/fiori-fundamentals/dist fiori-fundamentals
    $ shadow-cljs watch app

goto: [http://localhost:3000](http://localhost:3000)


Shadow-cljs does not yet support packaging .css assets ([shadow-cljs issue #353](https://github.com/thheller/shadow-cljs/issues/353)),
so symbolic link to `/node_modules/fiori-fundamentals/dist` is a kind of workaround.

## License

Copyright © 2019 rotaliator

Released under the MIT license.

