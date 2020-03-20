# Siguria2020



Në këtë projekt janë realizuar tri komanda kryesore, të cilat kanë edhe funksione(nënkomanda) tjera. Realizimin e tyre e kemi bërë me anë të gjuhës programuese Java.

Kuptohet që të tre kemi punuar bashkarisht në realizimin e këtij projekti.

@ylber-gashi më detajisht është marrë me **Main** funksionin dhe me komandën **morse-code**, përkatësisht me enkodimin dhe dekodimin.

@xhanan më detajisht është marrë me komandën **caesar** dhe me **beep** funksionin tek Morse kodi.

@rrustemh më detajisht është marrë me komandën **count**. 


### Komandat kryesore me funksionet apo nënkomandat përkatëse janë: 



1. **morse-code**
*   **encode**  (Alfabetin latin e kthen në Morse kod)
*   **decode** (Hyrjen si Morse kod e kthen në alfabet latin)
*   **beep**  (Hyrjen si alfabet latin e kthen në Morse kod dhe e shpreh me zë, për secilin karakter)
2. **caesar**
*   **encrypt** (Çdo karakter të tekstit hyrës e zhvendos për aq pozita sa është dhënë çelesi)
*   **decrypt** (Cipher tekstin e zhvendos për aq sa është dhënë çelesi, por në anën e kundërt në krahasim me enkriptimin)
*   **brute-force** (Provon 25 herë dekriptimin e një cipher teksti të dhënë, me 25 çelësa të ndryshëm)
3. **count**
*   **lines** (Numëron rreshtat në tekstin hyrës)
*   **words** (Numëron fjalët në tekstin hyrës)
*   **letters** (Numëron shkronjat në tekstin hyrës)
*   **symbols** (Numëron simbolet në tekstin hyrës, pra pa i përfshirë numrat, shkronjat dhe hapësirat)
*   **characters** (Numëron të gjitha karakteret në tekstin hyrës, anashkalohen vetëm hapësirat)
*   **vowels** (Numëron zanoret në tekstin hyrës)
*   **consonants** (Numëron bashkëtingëlloret në tekstin hyrës duke i zbritur numrin e zanoreve, hapësirave dhe simboleve nga gjatësia e tekstit hyrës)
*   **spaces**(Numëron hapësirat në tekstin hyrës)


### Për ekzekutimin e programit duhet ti ndjekni këto hapa:


1. Së pari e bëni compile duke përdorur komandën: **javac paketa\ds.java** (sigurohuni të jeni në path-in e duhur)
2. Së dyti, për të ekzekutuar një nga funksionet e programit duhet t’i shkruani komandat në këtë format: 

    -Per funksionin **_morse-code_**:


    **java paketa.ds morse-code encode/decode &lt;text>/&lt;morse code>**


    **java paketa.ds morse-code beep &lt;text>**


    -Per funksionin **_caesar_**:


    **java paketa.ds caesar encrypt/decrypt &lt;key> &lt;plain text>/&lt;cipher text>**


    **java paketa.ds caesar brute-force &lt;cipher text>**


    -Per funksionin **_count_**:


    **java paketa.ds count lines/words/letters/symbols &lt;text>**


    **java paketa.ds count characters/vowels/consonants/spaces &lt;text>**


### Shembuj të përdorimit të komandave:

	morse-code
  
  ![](https://raw.githubusercontent.com/ylber-gashi/Siguria2020/master/Images/1.PNG)

    



	caesar

![](https://github.com/ylber-gashi/Siguria2020/blob/master/Images/2.PNG)


    




	count

![](https://github.com/ylber-gashi/Siguria2020/blob/master/Images/3.PNG?raw=true)


    



**Referencat:**



1. [https://gist.github.com/Xyene/6478305?fbclid=IwAR3nkyPECY5aaOrOUDaYuoaWTlB5cCj8P6q2ZjaKqWYDydgeba2AQGK4_qc](https://gist.github.com/Xyene/6478305?fbclid=IwAR3nkyPECY5aaOrOUDaYuoaWTlB5cCj8P6q2ZjaKqWYDydgeba2AQGK4_qc) (Nga ku jemi bazuar për funksionin beep tek morse code)

2. [https://www.thejavaprogrammer.com/caesar-cipher-java-encryption-decryption/?fbclid=IwAR2gvwtkMhrTBIQ4C6wxrD55jYZhD2kNldBwvgDVLd3jxFpsvJMfuMA0QUM](https://www.thejavaprogrammer.com/caesar-cipher-java-encryption-decryption/?fbclid=IwAR2gvwtkMhrTBIQ4C6wxrD55jYZhD2kNldBwvgDVLd3jxFpsvJMfuMA0QUM) (Në të cilin jemi mbështetur gjatë realizimit të funksionit caesar)

3. [https://stackoverflow.com/a/5864174](https://stackoverflow.com/a/5864174) (Këtu jemi bazuar për ta realizuar komanden words tek funksioni count)
