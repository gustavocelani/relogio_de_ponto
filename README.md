# Relógio de Ponto

## Features

* Cálculo de saída
* Otimização do horário de almoço
* Alerta de saída

## Cobertura de Casos

```
+-------+---------+--------+-------+-------+--------+-------------------------------+
|   #   | Chegada | Almoço | Volta | Saída | Tempo  |             Ação              |
| Caso  |  (e1)   |  (s1)  | (e2)  | (s2)  | (hour) |   ( Calcular saída para: )    |
+-------+---------+--------+-------+-------+--------+-------------------------------+
|   1   |    1    |   0    |   0   |   0   |   0    | 1h de almoço e 8h de trabalho |
|   2   |    1    |   0    |   0   |   0   |   1    | 1h de almoço e Xh de trabalho |
|   3   |    1    |   1    |   0   |   0   |   0    | 1h de almoço e 8h de trabalho |
|   4   |    1    |   1    |   0   |   0   |   1    | 1h de almoço e Xh de trabalho |
|   5   |    1    |   1    |   1   |   0   |   0    | Xh de almoço e 8h de trabalho |
|   6   |    1    |   1    |   1   |   0   |   1    | Xh de almoço e Xh de trabalho |
+-------+---------+--------+-------+-------+--------+-------------------------------+
```

### Releases

* [Relógio de Ponto v1.0.0.0](https://github.com/gustavocelani/relogio_de_ponto/blob/master/release/Rel%C3%B3gio%20de%20Ponto.exe)
