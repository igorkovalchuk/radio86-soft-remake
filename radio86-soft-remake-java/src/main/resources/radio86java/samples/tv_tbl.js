// Basic source code: журнал "Радио" 1988 N7
// Author: Сорокин А.
// http://www.danbigras.ru/RK86/TV/TestTV.html

cls();

cur(20, 20);
println("УПРАВЛЯЮЩИЕ КЛАВИШИ");
printtab(15); println("НАЖМИТЕ УПРАВЛЯЮЩУЮ КЛАВИШУ");

q = [];

printtab(17);
print("ДЛЯ УВЕЛИЧЕНИЯ МАСШТАБА ");
q[0] = '1'; // q[0] = inkey(0);
println(q[0]);

printtab(13);
print("ДЛЯ ВОЗВРАТА В НАЧАЛЬНЫЙ МАСШТАБ ");
q[1] = '2';
println(q[1]);

printtab(10);
print("ДЛЯ ИЗМЕНЕНИЯ НАСТРАИВАЮЩЕЙ ТАБЛИЦЫ ");
q[2] = '3';
println(q[2]);

inkey(0);

bs = 32593; // 7F51

// *** ФОРМИРОВАНИЕ ШАХМАТНОГО ПОЛЯ В МАСШТАБЕ *** 
var w$ = "";
var b$ = "";

for(n = 1; n <= 64; n++) {
    b$ = b$ + " ";
    w$ = w$ + chr(23);
}

// page 1
var r = 1;
var z = 0;
var bl$ = "";
var wt$ = "";
var j = 0;
var i = 64;

// page 2
// var z;

//page 3
var a$ = [];
var x = 0;

reset1();
scale1();
display1();

log("input...");

var page = 1;

while(true) {
    key = inkey(0);
    if (key === q[0]) {
        scale(page);
        display(page);
    }
    else if (key === q[1]) {
        reset(page);
        scale(page);
        display(page);
    }
    else if (key === q[2]) {
        page += 1;
        log("page = " + page);
        if (page > 4) {
            log("break");
            break;
        }
        else {
            reset(page);
            scale(page);
            display(page);
        }
    }
}

function reset(n) {
    if (n === 1) {
        reset1();
    }
    else if (n === 2) {
        reset2();
    }
    else if (n === 3) {
        reset3();
    }
    else if (n === 4) {
        reset4();
    }
}

function scale(n) {
    if (n === 1) {
        scale1();
    }
    else if (n === 2) {
        scale2();
    }
    else if (n === 3) {
        scale3();
    }
    else if (n === 4) {
        scale4();
    }
}

function display(n) {
    if (n === 1) {
        display1();
    }
    else if (n === 2) {
        display2();
    }
    else if (n === 3) {
        display3();
    }
    else if (n === 4) {
        display4();
    }
}

function reset1() {
        r = 1;
        z = 0;
        bl$ = "";
        wt$ = "";
        j = 0;
        i = 64;
}

function scale1() {
    // *** ЦИКЛ ИЗМЕНЕНИЯ МАСШТАБА *** 
    if (i === 16) {
        i = 64; j = 25;
    }
    else {
        if (i === 64) {
            i = 0; r = 1;
        }
        j = j + 1;
        i = Math.floor(j * 1.6);
    }

    ay = Math.floor(25 / j);

    // ****  ЦИКЛ ФОРМИРОВАНИЯ СИМВОЛЬНЫХ ПЕРЕМЕННЫХ ****
    for(a = 0; a <= 64; a++) {
        if (z === 0) {
            bl$ = bl$ + b$.substr(0, i);
            wt$ = wt$ + w$.substr(0, i);
        }
        else if (z === 1) {
            bl$ = bl$ + w$.substr(0, i);
            wt$ = wt$ + b$.substr(0, i);
        }
        if (bl$.length > 64) {
            bl$ = bl$.substr(0, 64);
            wt$ = wt$.substr(0, 64);
        }
        if (bl$.length >= 64) {
            break;
        }
        z = z + 1;
        if (z === 2) {
            z = 0;
        }
    }

}

function display1() {
    cls();

    // **** ЦИКЛЫ ОТОБРАЖЕНИЯ ШАХМАТНОГО ПОЛЯ ****
    for(y = 0; y <= ay; y++) {
        for(t = 0; t <= (j - 1); t++) {
            ky = y * j + t;
            if (ky > 24) {
                break;
            }
            if (z === 0) {
                sh$ = wt$;
            }
            if (z === 1) {
                sh$ = bl$;
            }
            // ***** ЗАПОЛНЕНИЕ НИЖНЕЙ СТРОКИ *****
            if (ky === 0) {
                cur(0, 0);
                print(sh$.substr(0, 63));
                poke(bs, asc(sh$.substr(63, 1)));
            }
            else {
                cur(0, ky);
                print(sh$);
            }
        }
        // ***** ИЗМЕНЕНИЕ ПЕРЕМЕННОЙ ЦВЕТА ***** 
        z = z + 1;
        if (z === 2) {
            z = 0;
        }
    }

    bl$ = "";
    wt$ = "";
}

function reset2() {
    z = 6;
}

function scale2() {
    if ((z + 4) <= (6 + 4 + 4 + 4)) {
        z = z + 4;
    }
}

function display2() {
    cls();
    // **** ФОРМИРОВАНИЕ РАМОЧНОГО ПОЛЯ ****
    // **** ЦИКЛ ОТОБРАЖЕНИЯ РАМОЧНОГО ПОЛЯ ****
    for(x = 0; x <= 40; x+=z) {
        y = Math.floor(x / 2.6);
        plot(x, y, 1);
        line(x, 49 - y);
        line(127 - x, 49 - y);
        line(127 - x, y);
        line(x, y);
    }
}

function reset3() {
    cls();
    // ***** ЗАПИСЬ СИМВОЛОВ В МАССИВ *****
    a$[1] = "!";
    a$[2] = ".";
    a$[3] = "-";
    x = 0;
}

function scale3() {
    
}

function display3() {
    // **** ФОРМИРОВАНИЕ СИМВОЛЬНОГО ПОЛЯ *****
    s$ = "";
    x = x + 1;
    if (x === 4) {
        x = 1;
    }

    for(j = 1; j <= 64; j++) {
        s$ = s$ + a$[x];
    }

    // **** ПЕЧАТЬ НИЖНЕЙ СТРОКИ ****
    cur(0, 0);
    print(s$.substr(0,63));
    poke(bs, asc(a$[x]));

    // ***** ЦИКЛ ПЕЧАТИ *****
    for(i = 1; i <= 24; i++) {
        cur(0, i);
        print(s$);
    }
}

function reset4() {
}

function scale4() {
}

function display4() {
    // *** ФОРМИРОВАНИЕ ЦЕНТРАЛЬНОГО КРЕСТА И ДИАГОНАЛИ *****
    cls();
    for(i = 0; i <= 63; i++) { cur(i, 13); print("-"); }
    for(i = 0; i <= 24; i++) { cur(32, i); print("I"); }
    inkey(0);
    cls();
    for(i = 0; i <= 49; i++) { plot(i, 0, 1); line(127, 49 - i); }
    inkey(0);
    cls();
    for(i = 0; i <= 49; i++) { plot(0, i, 1); line(127, 49); }
    inkey(0);
}
