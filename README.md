# abnt-converter
Conversor de referencia ABNT para formato IEEE

A classe que contém todas as estratégias de conversão possíveis (que a ABNT determina) é business/converter.java. Confesso que há grande janela para menor repetição de código, porém, quando desenvolvi, foquei em cobrir o maior número de diferentes formas que um estudo pode estar na bibliografia.

Um arquivo file.properties deve ser criado e colocado na raiz do diretório. O arquivo deve conter o caminho para o arquivo que contém as referências.

Exemplo de conteúdo do arquivo a ser lido:

SCAVUZZO, M.; TAMBURRI, D. A.; NITTO, E. D. Providing Big Data Applications
with Fault-tolerant Data Migration Across Heterogeneous NoSQL Databases.
IEEE/ACM International Workshop on BIG Data Software Engineering, IEEE, mai. 2016.

SCHRAM, A.; ANDERSON, K. M. Design challenges/solutions for environments
supporting the analysis of social media data in crisis informatics research. International
Conference on System Sciences (HICSS), IEEE, jan. 2015.

O output do programa foi usado em um paper publicado no SEAA.
