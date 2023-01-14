echo off
rem mvn clean compile assembly:single
rem example: 
rem  -t bfg -in src/main/resources/fonts/default_125.fnt -out out/default_125.fofnt -wc -2 -xac -2 -lhc -2
rem convert.bat -t bfg -in src/main/resources/fonts/default_125.fnt -out out/default_125.fofnt -wc -2 -xac -2 -lhc -2

rem convert.bat -t bfg -in in/default_regular_10.fnt -out out/default_125_thin.fofnt -xc 1 -yc 1 -wc -2 -hc -2 -xac -2 -lhc 0 -oyc 0
rem convert.bat -t bfg -in in/default_regular_12.fnt -out out/default_125_thin.fofnt -wc -2 -hc -2 -xac -2 -lhc 0 -oyc 0
rem convert.bat -t bfg -in in/default_regular_14.fnt -out out/default_125_thin.fofnt -wc -2 -hc -2 -xac -2 -lhc 0 -oyc 0
rem convert.bat -t bfg -in in/default_regular_16.fnt -out out/default_125_thin.fofnt -wc -2 -hc -2 -xac -2 -lhc 0 -oyc 0
rem convert.bat -t bfg -in in/default_regular_18.fnt -out out/default_125_thin.fofnt -wc -2 -hc -2 -xac -2 -lhc 0 -oyc 0
rem convert.bat -t bfg -in in/default_regular_20.fnt -out out/default_125_thin.fofnt -wc -2 -hc -2 -xac -2 -lhc 0 -oyc 0
rem convert.bat -t bfg -in in/default_regular_24.fnt -out out/default_125_thin.fofnt -wc -2 -hc -2 -xac -2 -lhc 0 -oyc 0

rem convert.bat -t bfg -in in/default_bold_10.fnt -out out/default_125_thin.fofnt -xc 1 -yc 2 -wc -3 -hc -5 -xac -3 -lhc -3 -oyc 0
rem convert.bat -t bfg -in in/default_bold_12.fnt -out out/default_125_thin.fofnt -xc 1 -yc 2 -wc -2 -hc -4 -xac -2 -lhc -2 -oyc 0
rem convert.bat -t bfg -in in/default_bold_14.fnt -out out/default_125_thin.fofnt -xc 1 -yc 2 -wc -2 -hc -5 -xac -2 -lhc -3 -oyc 0
rem convert.bat -t bfg -in in/default_bold_16.fnt -out out/default_125_thin.fofnt -xc 1 -yc 2 -wc -2 -hc -5 -xac -2 -lhc -3 -oyc 0
rem convert.bat -t bfg -in in/default_bold_18.fnt -out out/default_125_thin.fofnt -xc 1 -yc 2 -wc -2 -hc -6 -xac -2 -lhc -4 -oyc 0
rem convert.bat -t bfg -in in/default_bold_20.fnt -out out/default_125_thin.fofnt -xc 1 -yc 2 -wc -2 -hc -2 -xac -2 -lhc 0 -oyc 0
rem convert.bat -t bfg -in in/default_bold_24.fnt -out out/default_125_thin.fofnt -xc 1 -yc 2 -wc -2 -hc -2 -xac -2 -lhc 0 -oyc 0

rem SS4 - regular

rem convert.bat -t bfg -in in/default_regular_12_ss4.fnt -out out/default_125_thin.fofnt -wc -2 -hc -2 -xac -2 -lhc 0 -oyc 0

java -jar  foclassic-font-converter.jar %*
