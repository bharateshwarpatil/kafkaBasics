public class TestPartitoner {


    public static void main(String[] args) {

        int numPart = 4;

         long hashcode=188121412311212513l;

        System.out.println(Math.abs("Systemasdgjasgdwqeyiyqwieysadsadjlasdjlsjdlqwieuyoiqwuepoqwipiepoqwilashkdhaskldhklashal;djsal;jdlugwqeieyqwiu".hashCode() % (numPart-1)));



    }
}
