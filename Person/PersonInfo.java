// Desenvolvido por Eduardo Haesbaert

package Person;

import java.util.Date;

public class PersonInfo {
    public long cpf;
    public long rg;
    public String name;
    public String dateOfBirth;
    public String cityOfBirth;

    // Instancia um novo objeto de PersonInfo com a informações recebidas por argumento.
    public PersonInfo(long c, long r, String n, String dob, String cob) {
        cpf = c;
        rg = r;
        name = n;
        dateOfBirth = dob;
        cityOfBirth = cob;
    }
}


