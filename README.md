# Siguria2020



Në këtë projekt janë realizuar tre funksione kryesore, të cilat kanë edhe nënfunksione tjera. Realizimin e tyre e kemi bërë me anë të gjuhës programuese Java.

Kuptohet që të tre kemi punuar bashkarisht në realizimin e këtij projekti.

### Funksionet janë: 



1. **morse-code**
*   **encode**  (Alfabetin latin e kthen në kod Morse)
*   **decode** (Hyrjen si kod Morse e kthen në alfabet latin)
*   **beep**  (Hyrjen si alfabet latin e kthen në morse dhe e shpreh me zë, për secilin karakter)
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
*   **Consonants** (Numëron bashkëtingëlloret në tekstin hyrës duke i zbritur numrin e zanoreve, hapësirave dhe simboleve nga gjatësia e tekstit hyrës)
*   **spaces**(Numëron hapësirat në tekstin hyrës)


### Për ekzekutimin e programit duhet ti ndjekni këto hapa:


1. Së pari e bëni compile duke perdorur komandën: **javac paketa\ds.java** (sigurohuni të jeni në path-in e duhur)
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

	**morse-code**
  
  ![Morse-code]()
Format: ![Alt Text](url)
    




    **caesar**

![caesar]()
Format: ![Alt Text](url)

    





    **count**

![count]()
Format: ![Alt Text](url)

    



**Referencat:**



1. [https://gist.github.com/Xyene/6478305?fbclid=IwAR3nkyPECY5aaOrOUDaYuoaWTlB5cCj8P6q2ZjaKqWYDydgeba2AQGK4_qc](https://gist.github.com/Xyene/6478305?fbclid=IwAR3nkyPECY5aaOrOUDaYuoaWTlB5cCj8P6q2ZjaKqWYDydgeba2AQGK4_qc) (Nga ku jemi bazuar për funksionin beep tek morse code)

2. [https://www.thejavaprogrammer.com/caesar-cipher-java-encryption-decryption/?fbclid=IwAR2gvwtkMhrTBIQ4C6wxrD55jYZhD2kNldBwvgDVLd3jxFpsvJMfuMA0QUM](https://www.thejavaprogrammer.com/caesar-cipher-java-encryption-decryption/?fbclid=IwAR2gvwtkMhrTBIQ4C6wxrD55jYZhD2kNldBwvgDVLd3jxFpsvJMfuMA0QUM) (Në të cilin jemi mbështetur gjatë realizimit të funksionit caesar)

3. [https://stackoverflow.com/a/5864174](https://stackoverflow.com/a/5864174) (Këtu jemi bazuar për ta realizuar komanden words tek funksioni count)
