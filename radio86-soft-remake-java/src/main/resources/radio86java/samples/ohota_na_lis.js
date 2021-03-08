// Basic source code: журнал "Радио" 1987 N3
// Author: А. Долгий

// ИГРОВАЯ ПРОГРАММА "ОХОТА НА ЛИС"
// ВЕРСИЯ ДЛЯ "РАДИО-86РК"

cls(); println(); println(); tab(15); println("ОХОТА НА ЛИС"); println(); println();

var e = 0; // ошибка или нет
var nm = 9; // максимум для введенного пользователем числа (сколько лис мы можем спрятать)
var n = 0; // введенное пользователем число

var nf = 0; // сколько лис мы будем прятать
var ff = 0; // сколько лис мы будем искать

var h = 0; var m = 0; var s = 0; // текущее время
var p = 0; // штрафные очки
var xh = 0; var yh = 0; // текущие координаты охотника

var a = ""; // символ полученный с клавиатуры
var dx = 0; // флажок приращения координаты охотника по оси x
var dy = 0;

var hm = 10; // максимально допустимое значение hours

const STATE_EXIT = -1; // флажок останова программы
const STATE_GAME_OVER = 0; // завершение текущей игры
const STATE_CONTINUE = 1; // нормальное состояние - продолжение игры

var state = STATE_CONTINUE;

do {
    nf = input("CKOЛЬKO ЛИС СПРЯТАТЬ");
    nf = parseFloat(nf);
    e = f_1500(nf, nm);
} while(e === 1);

// В оригинальном варианте индекс массивов начинается с 1, а не с нуля.
// здесь мы просто не используем ячейки с индексом = 0

// координаты лис
var xf = new Array(nf + 1);
var yf = new Array(nf + 1);

// флажок, найдена лиса (1) или нет (0)
var f = new Array(nf + 1);

do {
    state = STATE_CONTINUE;

    initialize();
    while(state === STATE_CONTINUE) {
        nextIteration();
    }

    if (state !== STATE_EXIT) {
        println();
        println("HA СТАРТ ПРИГЛАШАЕТСЯ СЛЕДУЮЩИЙ УЧАСТНИК.");
        pause(10);
    }

} while(state !== STATE_EXIT);



function initialize() {
    i = 1;
    do {
        xf[i] = Math.floor(Math.random() * 50); // 0 .. 49
        yf[i] = Math.floor(Math.random() * 20); // 0 .. 19
        if (xf[i] === 0 && yf[i] === 0) {
            // здесь находится охотник
            continue;
        }
        if (i === 1) {
            ff = 1;
        }
        else {
            for(j = 1; j <= (i - 1); j++) {
                if (xf[i] === xf[j] && yf[i] === yf[j]) {
                    // проверяем не имеет ли дубликатов выбранная позиция
                    continue;
                }
            }
        }
        f[i] = 0;
        tab(5); println("ЛИCA-" + chr(48 + i) + " - ГОТОВА.");
        log("Лиса " + i + ", x = " + xf[i] + ", y = " + yf[i]);
        i++;
    } while(i <= nf);

    if (nf !== 1) {
        do {
            ff = input("CKOЛЬKO ЛИС ВЫ БУДЕТЕ ИСКАТЬ");
            ff = parseFloat(ff);
            e = f_1500(ff, nf);
        } while(e === 1);
    }

    h = 0; m = 0; s = 0;
    p = 0;
    xh = 0; yh = 0;

    a = "";
    dx = 0; dy = 0;

    cls(); cur(0, 0);

    for(i = 1; i <= 20; i++) {
        println(); tab(10);
        for(j = 1; j <=50; j++) {
            print(".");
        }
    }

    println(); println(); println(); println();
    cur(4, 4); println("CTAPT>" + chr(9));
    println();
    println("OTCЧET ВРЕМЕНИ НАЧНЕТСЯ ПОСЛЕ НАЖАТИЯ ЛЮБОЙ КЛАВИШИ.");
    inkey(0);

    cur(0, 2);
    println("[С]ЕВЕР " + chr(11) + ", [Ю]Г " + chr(15) + ", [В]ОСТОК " + chr(14) + ", [З]АПАД " + chr(29) + ", ПРОБЕЛ, ESC");
}



function nextIteration() {
    cur(0, 3); println("ВРЕМЯ: " + h + " ЧАС " + m + " MИH " + s + " CEK ");

    if (h > hm) {
        println("K СОЖАЛЕНИЮ, BAШE ВРЕМЯ ИСТЕКЛО.");
        state = STATE_GAME_OVER;
        return;
    }

    if (s === 0) {
        f_2000();
    }

    if (a !== "") {
        if (a === "esc")   {
            println("ПОИСК ПРЕКРАЩЕН.");
            state = STATE_EXIT;
            return;
        }

        dx = 0;
        dy = 0;
        cur(0, 2); println(spc(60));

        if (a === "с") {
            dy = 1;
        }
        else if (a === "ю") {
            dy = -1;
        }
        else if (a === "в") {
            dx = 1;
        }
        else if (a === "з") {
            dx = -1;
        }
    }

    cur(10 + xh, 4 + yh);
    println(".");
    xh = xh + dx; yh = yh + dy;

    if (!(xh > -1 && xh < 50 && yh > -1 && yh < 20)) {
        p = p + 1;
        cur(35, 3); println("ШTPAФHЫЕ ОЧКИ:" + p);
        print("ЗA ВЫХОД ИЗ ПЛОЩАДКИ ВЫ");
        if (p > 2) {
            println(" ДИСКВАЛИФИЦИРОВАНЫ");
            state = STATE_GAME_OVER;
            return;
        }
        println(" ВОЗВРАЩЕНЫ НА СТАРТ.");
        xh = 0; yh = 0; dx = 0; dy = 0;
    }

    cur(10 + xh, 4 + yh); println(chr(9));

    // сравнение координат охотника с координатами лис
    var d = 0;
    for(i = 1; i <= nf; i++) {
        if (f[i] === 1) {
            d = d + 1;
            continue;
        }
        if (xf[i] !== xh || yf[i] !== yh) {
            continue;
        }
        if ((dx !== 0 || dy !== 0) && Math.random() < 0.5) {
            continue;
        }
        cur(0, 2);
        println("НАЙДЕНА ЛИСА-" + chr(48+i));
        f[i] = 1; d = d + 1; dx = 0; dy = 0;
    }
    if (d > (ff - 1)) {
        println("ПOИCK ЗАКОНЧЕН, ПОЗДРАВЛЯЮ! ");
        state = STATE_GAME_OVER;
        return;
    }

    s = s + 10;
    if (s > 59) {
        s = 0; m = m + 1;
    }
    if (m > 59) {
        m = 0; h = h + 1;
    }

    a = "";
    // если этот цикл занимает 1 секунду, то на часах пройдет 10 секунд
    for(z = 1; z <= 4; z++) {
        var a1 = inkey(1);
        if (a1 !== "") {
            a = a1;
        }
        pause(0.25);
    }

}


// проверяем допустимое ли число ввел пользователь
function f_1500(n, nm) {
  var e = 0;

  var t$ = "* НУЖНО ВЫЛО ВВЕСТИ";

  if (n !== Math.floor(n)) {
    e = 1;
    t$ = t$+" ЦЕЛОЕ";
  }

  if (n < 0) {
    e = 1;
    t$ = t$ + " ПОЛОЖИТЕЛЬНОЕ";
  }

  t$ = t$ + " ЧИСЛО";

  if (n === 0 || n > nm) {
    e = 1;
    t$ = t$ + " ОТ 1 ДO " + nm;
  }

  if (e) {
      println(t$ + ".");
      print("ПОВТОРИТЕ, ");
      pause(1);
  }
  return e;
}


// показываем направление в котором искать лису и найденных лис
function f_2000() {
    for(i = 1; i <= nf; i++) {
        if (f[i] === 0) {
            xx = xf[i] - xh;
            yy = yf[i] - yh;
            if (Math.abs(xx) > Math.abs(yy)) {
                c$="З";
                if (xx > 0) {
                    c$ = "В";
                }
            }
            else {
                c$ = "Ю";
                if (yy > 0) {
                    c$ = "С";
                }
            }
        }
        else if (f[i] === 1) {
            cur(10 + xf[i], 4 + yf[i]);
            println(chr(48 + i));
            c$ = chr(23); // chr(127) - белый прямоугольник
        }

        cur(0, 22 - i);
        println("ЛИCA-" + chr(48+i) + " " + c$);
    }
}
