/**
 * This class serves to "prime the pump" on the database upon start up. This allows for easier testing, tweaking, and
 * overall a better developing experience. Currently the class only has one method, which handles the creation of all
 * the data.
 */

package edu.oswego.cs.rest;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Startup
@Singleton
public class PopulationData {

    @PostConstruct
    public void populateDataBase() {

        var db = new DatabaseController();

        db.storeStockImages();

        createMovie1(db);
        createMovie2(db);
        createMovie3(db);
        createMovie4(db);
        createMovie5(db);
        createMovie6(db);
        createMovie7(db);
        createMovie8(db);
        createMovie9(db);
        createMovie10(db);
        createMovie11(db);
        createMovie12(db);
        createMovie13(db);
        createMovie14(db);
        createMovie15(db);
        createMovie16(db);
        createMovie17(db);
        createMovie18(db);
        createMovie19(db);
        createMovie20(db);
        createMovie21(db);
        createMovie22(db);
        createMovie23(db);
        createMovie24(db);
        createMovie25(db);
        createMovie26(db);
        createMovie27(db);
        createMovie28(db);
        createMovie29(db);
        createMovie30(db);
        createMovie31(db);
        createMovie32(db);
        createMovie33(db);
        createMovie34(db);
        createMovie35(db);
        createMovie36(db);
        createMovie37(db);
        createMovie38(db);
        createMovie39(db);
        createMovie40(db);
        createMovie41(db);
        createMovie42(db);
        createMovie43(db);
        createMovie44(db);
        createMovie45(db);
        createMovie46(db);
        createMovie47(db);
        createMovie48(db);
        createMovie49(db);
        createMovie50(db);
        createMovie51(db);
        createMovie52(db);
        createMovie53(db);
        createMovie54(db);
        createMovie55(db);
        createMovie56(db);
        createMovie57(db);
        createMovie58(db);
        createMovie59(db);
        createMovie60(db);
        createMovie61(db);
        createMovie62(db);
        createMovie63(db);
        createMovie64(db);
        createMovie65(db);
        createMovie66(db);
        createMovie67(db);
        createMovie68(db);
        createMovie69(db);
        createMovie70(db);

    }

    /*
          Movie: Sound of Music
          Tags: 4
          Ratings: 4
          Reviews: 3
         */
    private void createMovie1(DatabaseController db) {
        db.createMovie("The Sound of Music", "Robert Wise", "1965", "2h. 52min.", "Georg Hurdalek, Howard Lindsay, Russel Crouse", "A nun ends up getting kicked out of the monastery after falling in love with a man and he has like 5 kids. Tragic, but he’s rich so it’s not so bad. He’s divorced. It’s a musical.");

        var movie1 = db.getMovieWithTitle("The Sound of Music");
        db.createTag("Romance", movie1.get().getId(), "David", "public");
        db.createTag("Drama", movie1.get().getId(), "David", "public");
        db.createTag("Family", movie1.get().getId(), "David", "public");
        db.createTag("Biography", movie1.get().getId(), "David", "public");

        db.createRating("Blasphemy Against Vows", "5", "5", "David", movie1.get().getId(), "public");
        db.createRating("Is it worth it?", "10", "10", "David", movie1.get().getId(), "public");
        db.createRating("A cute film", "3", "3", "David", movie1.get().getId(), "public");
        db.createRating("Best Musical", "10", "10", "David", movie1.get().getId(), "public");

        db.createReview(movie1.get().getId(), "Much music very wow!", "David", "public");
        db.createReview(movie1.get().getId(), "Very music much wow!", "Keith", "public");
        db.createReview(movie1.get().getId(), "There was in fact music.", "Binura", "public");

        db.createActor("Julie Andrews", "10/01/1935", movie1.get().getId());
        db.createActor("Christopher Plummer", "12/13/1929", movie1.get().getId());
        db.createActor("Eleanor Parker", "06/26/1922", movie1.get().getId());
    }

    /*
      Movie: 17 again
      Tags: 3
      Ratings: 3
      Reviews: 1
     */
    private void createMovie2(DatabaseController db) {
        db.createMovie("17 Again", "Burr Steers", "2009", "1h. 42min.", "Jason Filardi",
                "An ungrateful middle-aged man gets the chance to be 17 again because he had a fixation on his high-school glory days and gets a chance to be “17 again” by a magical janitor. His best friend was cool. His wife rightfully wants to divorce him after putting up with his ridiculousness for 20 years. But then he learns to appreciate what he has or something and she decides not to divorce him (unfortunately).");

        var movie2 = db.getMovieWithTitle("17 Again");
        db.createTag("Comedy", movie2.get().getId(), "David", "public");
        db.createTag("Drama", movie2.get().getId(), "David", "public");
        db.createTag("Fantasy", movie2.get().getId(), "David", "public");

        db.createRating("They should get divorced", "3", "3", "David", movie2.get().getId(), "public");
        db.createRating("The best friend was the best character", "7", "7", "David", movie2.get().getId(), "public");
        db.createRating("How cute was Zac Efron", "10", "10", "David", movie2.get().getId(), "public");

        db.createReview(movie2.get().getId(), "A movie for sure.", "David", "public");

        db.createActor("Zac Efron", "10/18/1987", movie2.get().getId());
        db.createActor("Matthew Perry", "08/19/1922", movie2.get().getId());
        db.createActor("Leslie Mann", "03/26/1972", movie2.get().getId());
    }

    /*
      Movie: How to Train Your Dragon
      Tags: 3
      Ratings: 3
      Reviews: 1
     */
    private void createMovie3(DatabaseController db) {
        db.createMovie("How to Train Your Dragon", "Dean DeBlois, Chris Sanders", "2010", "1h. 38min.", "William Davies, Dean DeBlois, Chris Sanders",
                "A young viking befriends a cat-like dragon. He also gets a date.");

        var movie3 = db.getMovieWithTitle("How to Train Your Dragon");
        db.createTag("Animation", movie3.get().getId(), "David", "public");
        db.createTag("Action", movie3.get().getId(), "David", "public");
        db.createTag("Adventure", movie3.get().getId(), "David", "public");

        db.createRating("Good animated movies", "6", "6", "David", movie3.get().getId(), "public");
        db.createRating("Cute pet", "7", "7", "David", movie3.get().getId(), "public");
        db.createRating("Would watch again", "5", "5", "David", movie3.get().getId(), "public");

        db.createReview(movie3.get().getId(), "Dragons are very cool.", "David", "public");

        db.createActor("Jay Baruchel", "04/09/1982", movie3.get().getId());
        db.createActor("Gerard Butler", "11/13/1969", movie3.get().getId());
        db.createActor("Christopher Mintz-Plasse", "06/20/1989", movie3.get().getId());
    }

    /*
      Movie: Ratatouilleie
      Tags: 3
      Ratings: 3
      Reviews: 4
     */
    private void createMovie4(DatabaseController db) {
        db.createMovie("Ratatouille", "Brad Bird, Jan Pinkava", "2007", "1h. 51min.", "Brad Bird, Jan Pinkava, Jim Capobianco",
                "A rat can cook and cooks for the son of a famous chef.");

        var movie4 = db.getMovieWithTitle("Ratatouille");
        db.createTag("Animation", movie4.get().getId(), "David", "public");
        db.createTag("Adventure", movie4.get().getId(), "David", "public");
        db.createTag("Comedy", movie4.get().getId(), "David", "public");

        db.createRating("Strong rats", "6", "6", "David", movie4.get().getId(), "public");
        db.createRating("rats everywhere", "7", "7", "David", movie4.get().getId(), "public");
        db.createRating("Too many rats", "5", "5", "David", movie4.get().getId(), "public");

        db.createReview(movie4.get().getId(), "Rats touching food, don't watch this movie if you are eating a turkey sub from Subway.", "David", "public");
        db.createReview(movie4.get().getId(), "Theres great colors, music, and character development whats not to love?", "Keith", "public");
        db.createReview(movie4.get().getId(), "This movie was an absolute snooze-fest, no action means no fun.", "Binura", "public");
        db.createReview(movie4.get().getId(), "A childhood favorite of mine!", "Mahella", "public");

        db.createActor("Brad Garrett", "04/14/1960", movie4.get().getId());
        db.createActor("Lou Romano", "04/15/1971", movie4.get().getId());
        db.createActor("Paton Oswalt", "01/27/1969", movie4.get().getId());
    }

    /*
      Movie: Lilo and Stitch
      Tags: 4
      Ratings: 3
      Reviews: 1
     */
    private void createMovie5(DatabaseController db) {
        db.createMovie("Lilo and Stitch", "Dean DeBlois, Chris Sanders", "2002", "1h. 25min.", "Chris Sanders, Dean DeBlois",
                "A girl named Lilo ends up adapting an alien by accident and things go down.");

        var movie5 = db.getMovieWithTitle("Lilo and Stitch");
        db.createTag("Animation", movie5.get().getId(), "David", "public");
        db.createTag("Adventure", movie5.get().getId(), "David", "public");
        db.createTag("Comedy", movie5.get().getId(), "David", "public");
        db.createTag("Family", movie5.get().getId(), "David", "public");

        db.createRating("Best Movies of all time", "10", "10", "David", movie5.get().getId(), "public");
        db.createRating("Best family movies", "6", "6", "David", movie5.get().getId(), "public");
        db.createRating("Paul Approved", "10", "10", "David", movie5.get().getId(), "public");

        db.createReview(movie5.get().getId(), "One of my favorite family movies!", "Mahella", "public");
    }

    /*
    Movie: Atlantis: The Lost Empire
    Tags: 3
          Ratings: 2
          Reviews: 0
         */
    private void createMovie6(DatabaseController db) {
        db.createMovie("Atlantis: The Lost Empire", "Gary Trousdale, Kirk Wise", "2001", "1h. 35min.", "Tab Murphy, Kirk Wise, Gary Trousdale",
                "A failure of a researcher who’s super dorky ends up going on an expedition to Atlantis, this is an attractive man. And he gets a hot girlfriend at the end of the movie.");

        var movie6 = db.getMovieWithTitle("Atlantis: The Lost Empire");
        db.createTag("Animation", movie6.get().getId(), "David", "public");
        db.createTag("Action", movie6.get().getId(), "David", "public");
        db.createTag("Adventure", movie6.get().getId(), "David", "public");

        db.createRating("Animated movies with somber deaths", "10", "10", "David", movie6.get().getId(), "public");
        db.createRating("Paul Approved", "10", "10", "David", movie6.get().getId(), "public");

        db.createActor("Michael J. Fox", "06/09/1961", movie6.get().getId());
        db.createActor("Jim Varney", "06/15/1949", movie6.get().getId());
        db.createActor("Corey Burton", "08/03/1955", movie6.get().getId());
    }

    private void createMovie7(DatabaseController db) {
        db.createMovie("West Side Story", "Jerome Robbins, Robert Wise", "1961", "2h. 33min.", "Ernest Lehman, Arthur Laurents, Jerome Robbins",
                "Lots of good-looking people fight like Romeo and Juliet in a modern gang thing. It’s a musical.");

        var movie7 = db.getMovieWithTitle("West Side Story");
        db.createTag("Crime", movie7.get().getId(), "David", "public");
        db.createTag("Drama", movie7.get().getId(), "David", "public");
        db.createTag("Musical", movie7.get().getId(), "David", "public");

        db.createRating("Best musical", "10", "10", "David", movie7.get().getId(), "public");
        db.createRating("Feel good movies", "10", "10", "David", movie7.get().getId(), "public");
    }

    private void createMovie8(DatabaseController db) {
        db.createMovie("Bring It On", "Peyton Reed", "2000", "1h. 38min.", "Jessica Bendinger",
                "A chick who’s on a cheerleading team that steals routines from another way cooler school ends up being captain and changes her team’s ways. They end up losing to the way cooler team.");

        var movie8 = db.getMovieWithTitle("Bring It On");
        db.createTag("Comedy", movie8.get().getId(), "David", "public");
        db.createTag("Romance", movie8.get().getId(), "David", "public");
        db.createTag("Sport", movie8.get().getId(), "David", "public");

        db.createRating("Best Cheerleading movie", "6", "6", "David", movie8.get().getId(), "public");
        db.createRating("Cutest Outfits", "3", "3", "David", movie8.get().getId(), "public");
    }

    private void createMovie9(DatabaseController db) {
        db.createMovie("Enchanted", "Kevin Lima", "2007", "1h. 47min.", "Bill Kelly",
                "An animated fairy-tale chick ends up falling into a well and ends up getting with a man who’s engaged. She was also engaged and having her wedding day before she fell into the well. They end up having an emotional affair and get together at the end of the movie. It’s so cute and their partners get together and live way better lives than them.");
        var movie9 = db.getMovieWithTitle("Enchanted");
        db.createTag("Animation", movie9.get().getId(), "David", "public");
        db.createTag("Adventure", movie9.get().getId(), "David", "public");
        db.createTag("Comedy", movie9.get().getId(), "David", "public");

        db.createRating("Best Movie of ALL TIME", "10", "10", "David", movie9.get().getId(), "public");
        db.createRating("Best side character of all time", "3", "3", "David", movie9.get().getId(), "public");
        db.createRating("Best storyline of all time", "10", "10", "David", movie9.get().getId(), "public");
        db.createRating("Most satisfying ending of all time", "4", "4", "David", movie9.get().getId(), "public");
    }

    private void createMovie10(DatabaseController db) {
        db.createMovie("The Road to El Dorado", "Bibo Bergeron, Don Paul, Jeffrey Katzenberg", "2000", "1h. 29min.", "Ted Elliot, Terry Rossio, Karey Kirkpatrick",
                "Two brother-like friends end up stranded on an island and deceive a bunch of natives into thinking they’re gods. They end up saving the island and leaving it though.");

        var movie10 = db.getMovieWithTitle("The Road to El Dorado");
        db.createTag("Animation", movie10.get().getId(), "David", "public");
        db.createTag("Adventure", movie10.get().getId(), "David", "public");
        db.createTag("Comedy", movie10.get().getId(), "David", "public");

        db.createRating("BEST movie of all time", "10", "10", "David", movie10.get().getId(), "public");
        db.createRating("Best bros United", "8", "8", "David", movie10.get().getId(), "public");
        db.createRating("Summer-time movies", "9", "9", "David", movie10.get().getId(), "public");
        db.createRating("Paul Approved", "10", "10", "David", movie10.get().getId(), "public");
    }

    /*
     *
     */
    private void createMovie11(DatabaseController db) {
        db.createMovie("Barbie", "Greta Gerwig", "2023", "1h. 45min.", "Greta Gerwig, Noah Baumbach",
                "Barbie becomes imperfect and wants to fix herself so she goes to the real world and then wants to stay imperfect. Ken is also a menace.");

        var movie11 = db.getMovieWithTitle("Barbie");
        db.createTag("Adventure", movie11.get().getId(), "David", "public");
        db.createTag("Comedy", movie11.get().getId(), "David", "public");
        db.createTag("Fantasy", movie11.get().getId(), "David", "public");

        db.createRating("Camp movies", "10", "10", "David", movie11.get().getId(), "public");
        db.createRating("Existential Crises", "5", "5", "David", movie11.get().getId(), "public");
        db.createRating("2023 Movies", "8", "8", "David", movie11.get().getId(), "public");

        db.createReview(movie11.get().getId(), "I'm just Ken.", "David", "public");

    }

    private void createMovie12(DatabaseController db) {
        db.createMovie("Barbie as The Princess and the Pauper", "William Lau", "2004", "1h. 25min.", "Cliff Ruby, Elana Lesser, Mark Twain",
                "An adaption of the prince and the pauper but with Barbie.");

        var movie12 = db.getMovieWithTitle("Barbie as The Princess and the Pauper");
        db.createTag("Animation", movie12.get().getId(), "David", "public");
        db.createTag("Comedy", movie12.get().getId(), "David", "public");
        db.createTag("Family", movie12.get().getId(), "David", "public");

        db.createRating("Best Barbie movie of all time", "10", "10", "David", movie12.get().getId(), "public");
        db.createRating("Best Barbie movie of all time", "7", "10", "Keith", movie12.get().getId(), "public");
        db.createRating("Best animated movie of all time", "5", "5", "David", movie12.get().getId(), "public");
        db.createRating("Cutest Couples in Movies", "2", "2", "David", movie12.get().getId(), "public");
    }

    private void createMovie13(DatabaseController db) {
        db.createMovie("Cinderella", "Robert Iscove", "1997", "1h. 28min.", "Oscar Hammerstein 2, Robert L. Freedman, Charles Perrault",
                "A chick who can’t throw hands ends up meeting a prince and they live happily ever after.");

        var movie13 = db.getMovieWithTitle("Cinderella");
        db.createTag("Family", movie13.get().getId(), "David", "public");
        db.createTag("Fantasy", movie13.get().getId(), "David", "public");
        db.createTag("Musical", movie13.get().getId(), "David", "public");

        db.createRating("Live-Action Princess Movie", "4", "4", "David", movie13.get().getId(), "public");
        db.createRating("Confusing movies", "10", "10", "David", movie13.get().getId(), "public");

        db.createActor("Brandy Norwood", "02/11/1979", movie13.get().getId());
        db.createActor("Bernadette Peters", "02/28/1948", movie13.get().getId());
    }

    private void createMovie14(DatabaseController db) {
        db.createMovie("The Lion King", "Roger Allers, Rob Minkoff", "1994", "1h. 28min.", "Irene Mecchi, Jonathan Roberts, Linda Woolverton",
                "Young lion cub murders his father cause he can’t follow instructions. He then fights his uncle to take his father’s place as leader of the lion pack.");

        var movie14 = db.getMovieWithTitle("The Lion King");
        db.createTag("Animation", movie14.get().getId(), "David", "public");
        db.createTag("Adventure", movie14.get().getId(), "David", "public");
        db.createTag("Drama", movie14.get().getId(), "David", "public");

        db.createRating("Best animated movie of all time", "7", "7", "David", movie14.get().getId(), "public");

        db.createReview(movie14.get().getId(), "Ah Zabenya", "David", "public");
    }

    private void createMovie15(DatabaseController db) {
        db.createMovie("The Princess and the Frog", "Ron Clements, John Musker", "2009", "1h. 37min.", "Ron Clements, John Musker, Greg Erb",
                "A girl who has life figured out gets bothered by a frog and her life becomes awful. But they fall in love and live happily ever after.");

        var movie15 = db.getMovieWithTitle("The Princess and the Frog");
        db.createTag("Animation", movie15.get().getId(), "David", "public");
        db.createTag("Adventure", movie15.get().getId(), "David", "public");
        db.createTag("Comedy", movie15.get().getId(), "David", "public");

        db.createRating("Animated Disney Movie Goodness", "2", "2", "David", movie15.get().getId(), "public");
        db.createRating("Princess PrincessNess", "8", "8", "David", movie15.get().getId(), "public");
        db.createRating("Aesthetic", "6", "6", "David", movie15.get().getId(), "public");
    }

    private void createMovie16(DatabaseController db) {
        db.createMovie("Tangled", "Nathan Greno, Byron Howard", "2010", "1h. 40min.", "Dan Fogelman, Jacob Grimm, Wilhelm Grimm",
                "A girl leaves the tower that her mom doesn’t want her to and falls in love. She was also imprisoned in said tower for years. And stolen from her parents.");

        var movie16 = db.getMovieWithTitle("Tangled");
        db.createTag("Animation", movie16.get().getId(), "David", "public");
        db.createTag("Adventure", movie16.get().getId(), "David", "public");
        db.createTag("Comedy", movie16.get().getId(), "David", "public");

        db.createRating("Disney Princess Movie", "10", "10", "David", movie16.get().getId(), "public");
        db.createRating("Quirky Disney Character rating", "7", "7", "David", movie16.get().getId(), "public");
        db.createRating("Best animated movie", "8", "8", "David", movie16.get().getId(), "public");
    }

    private void createMovie17(DatabaseController db) {
        db.createMovie("Mulan", "Tony Bancroft, Barry Cook", "1998", "1h. 27 min.", "Robert D. San Souci, Rita Hsiao, Chris Sanders",
                "A girl goes to war in place of her father and saves China. She also has a pet dragon and takes home a hot general.");

        var movie17 = db.getMovieWithTitle("Mulan");
        db.createTag("Animation", movie17.get().getId(), "David", "public");
        db.createTag("Adventure", movie17.get().getId(), "David", "public");
        db.createTag("Comedy", movie17.get().getId(), "David", "public");

        db.createRating("Disney Princess Movie", "10", "10", "David", movie17.get().getId(), "public");
        db.createRating("Princess PrincessNess", "8", "8", "David", movie17.get().getId(), "public");
        db.createRating("Best animated movie of all time", "10", "10", "David", movie17.get().getId(), "public");
        db.createRating("Best Movies of all time", "10", "10", "David", movie17.get().getId(), "public");
    }

    private void createMovie18(DatabaseController db) {
        db.createMovie("Shang-Chi and the Legend of the 10 Rings", "Destin Daniel Cretton", "2021", "2h. 12 min.", "Dave Callaham, Destin Daniel Cretton, Andrew Lanham",
                "A boy becomes a superhero because he has to fight his father or something.");

        var movie18 = db.getMovieWithTitle("Shang-Chi and the Legend of the 10 Rings");
        db.createTag("Action", movie18.get().getId(), "David", "public");
        db.createTag("Adventure", movie18.get().getId(), "David", "public");
        db.createTag("Comedy", movie18.get().getId(), "David", "public");

        db.createRating("Superhero origins", "4", "4", "David", movie18.get().getId(), "public");
        db.createRating("Cutest male lead", "9", "9", "David", movie18.get().getId(), "public");
        db.createRating("Best weapons", "7", "7", "David", movie18.get().getId(), "public");

        db.createActor("Simu Liu", "04/19/1989", movie18.get().getId());
        db.createActor("Awkwafina", "06/02/1988", movie18.get().getId());
    }

    private void createMovie19(DatabaseController db) {
        db.createMovie("Everything Everywhere All at Once", "Daniel Kwan, Daniel Scheinert", "2022", "2h. 19min.", "Daniel Kwan, Daniel Scheinert",
                "A middle-aged Chinese immigrant goes on an adventure exploring other universes.");

        var movie19 = db.getMovieWithTitle("Everything Everywhere All at Once");
        db.createTag("Action", movie19.get().getId(), "David", "public");
        db.createTag("Adventure", movie19.get().getId(), "David", "public");
        db.createTag("Comedy", movie19.get().getId(), "David", "public");

        db.createRating("Costume rating", "7", "7", "David", movie19.get().getId(), "public");
        db.createRating("Weirdest people", "4", "4", "David", movie19.get().getId(), "public");
        db.createRating("Coolest plot", "3", "3", "David", movie19.get().getId(), "public");
    }

    private void createMovie20(DatabaseController db) {
        db.createMovie("Spider-Man: Across the Spider-Verse", "Joaquim Dos Santos, Kemp Powers, Justin K. Thompson", "2023", "2h. 20min.", "Phil Lord, Christopher Miller, Dave Callaham",
                "Young Spider Boy has another spider-verse adventure.");

        var movie20 = db.getMovieWithTitle("Spider-Man: Across the Spider-Verse");
        db.createTag("Animation", movie20.get().getId(), "David", "public");
        db.createTag("Action", movie20.get().getId(), "David", "public");
        db.createTag("Adventure", movie20.get().getId(), "David", "public");

        db.createRating("Best animated movie", "10", "10", "David", movie20.get().getId(), "public");
        db.createRating("Movie of the year", "9", "9", "David", movie20.get().getId(), "public");
        db.createRating("Character drawing rating", "3", "3", "David", movie20.get().getId(), "public");
        db.createRating("Animation awesomeness", "4", "4", "David", movie20.get().getId(), "public");

        db.createReview(movie20.get().getId(), "One of the best animated superhero movies of all time!", "David", "public");
        db.createReview(movie20.get().getId(), "The soundtrack was a work of art!", "Binura", "public");
    }

    private void createMovie21(DatabaseController db) {
        db.createMovie("The Little Mermaid", "Ron Clements, John Musker", "1989", "1h 23min", "Ron Clements, John Musker", "Mermaid girl saves prince, thinks he’s handsome and goes to land. Lives happily ever after on land.");

        var movie21 = db.getMovieWithTitle("The Little Mermaid");
        db.createTag("Animation", movie21.get().getId(), "Apple", "public");
        db.createTag("Family", movie21.get().getId(), "Apple", "public");
        db.createTag("Fantasy", movie21.get().getId(), "Apple", "public");

        db.createRating("Is it worth it?", "10", "10", "Apple", movie21.get().getId(), "public");
        db.createRating("The sea is amazing", "7", "7", "Apple", movie21.get().getId(), "public");
        db.createRating("Favorite Child Syndrome", "6", "6", "Apple", movie21.get().getId(), "public");
        db.createRating("Spoiled children", "3", "3", "Apple", movie21.get().getId(), "public");
    }

    private void createMovie22(DatabaseController db) {
        db.createMovie("Chicken Little", "Mark Dindal", "2005", "1h 21min", "Mark Dindal, Steve Bencich, Ron J. Friedman", "A little chicken boy finds out that aliens are coming, tries to warn everyone, gets gaslit by his town, and eventually everyone finds out he was right.");

        var movie22 = db.getMovieWithTitle("Chicken Little");
        db.createTag("Animation", movie22.get().getId(), "Cherry", "public");
        db.createTag("Adventure", movie22.get().getId(), "Cherry", "public");
        db.createTag("Comedy", movie22.get().getId(), "Cherry", "public");

        db.createRating("Motherless child", "10", "10", "Cherry", movie22.get().getId(), "public");
        db.createRating("Interspecies relationships", "4", "4", "Cherry", movie22.get().getId(), "public");
        db.createRating("Aliens", "7", "7", "Cherry", movie22.get().getId(), "public");
    }

    private void createMovie23(DatabaseController db) {
        db.createMovie("Sleeping Beauty", "Clyde Geronimi", "1959", "1h 15min", "Charles Perrault (based on the story)", "A child is the victim of her parents' sins, gets raised by 3 old ladies, and is saved by a prince from a dragon.");

        var movie23 = db.getMovieWithTitle("Sleeping Beauty");
        db.createTag("Animation", movie23.get().getId(), "Banana", "public");
        db.createTag("Family", movie23.get().getId(), "Banana", "public");
        db.createTag("Fantasy", movie23.get().getId(), "Banana", "public");

        db.createRating("Parents suck", "4", "4", "Banana", movie23.get().getId(), "public");
        db.createRating("Fairies useless", "9", "9", "Banana", movie23.get().getId(), "public");
        db.createRating("Talking animals", "2", "2", "Banana", movie23.get().getId(), "public");
    }

    private void createMovie24(DatabaseController db) {
        db.createMovie("Happily N'Ever After", "Paul J. Bolger", "2006", "1h 27min", "Paul J. Bolger, Robert Moreland", "Cinderella rendition with a girl falling in love with a prince but the prince is trash so she ends up with the dishwasher.");

        var movie24 = db.getMovieWithTitle("Happily N'Ever After");
        db.createTag("Animation", movie24.get().getId(), "Mango", "public");
        db.createTag("Adventure", movie24.get().getId(), "Mango", "public");
        db.createTag("Comedy", movie24.get().getId(), "Mango", "public");

        db.createRating("Romance", "4", "4", "Mango", movie24.get().getId(), "public");
        db.createRating("Craziness", "8", "8", "Mango", movie24.get().getId(), "public");
        db.createRating("Forced relationship", "5", "5", "Mango", movie24.get().getId(), "public");
    }

    private void createMovie25(DatabaseController db) {
        db.createMovie("Happily N'Ever After 2: Snow White: Another Bite at the Apple", "Steven E. Gordon, Boyd Kirkland", "2009", "1h 10min", "Chris Denk, Rob Moreland", "A spunky princess end up learning to sympathize with people and realizes there’s more to life than getting everything you want.");

        var movie25 = db.getMovieWithTitle("Happily N'Ever After 2: Snow White: Another Bite at the Apple");
        db.createTag("Animation", movie25.get().getId(), "Melon", "public");
        db.createTag("Adventure", movie25.get().getId(), "Melon", "public");
        db.createTag("Comedy", movie25.get().getId(), "Melon", "public");

        db.createRating("Superficial princess", "7", "7", "Melon", movie25.get().getId(), "public");
        db.createRating("Character development", "6", "6", "Melon", movie25.get().getId(), "public");
        db.createRating("Villain plan", "8", "8", "Melon", movie25.get().getId(), "public");
    }

    private void createMovie26(DatabaseController db) {
        db.createMovie("The Swan Princess", "Richard Rich", "1994", "1h 29min", "Richard Rich, Brian Nissen", "Two bratty children who hate each other are engaged and bully each other throughout their childhoods. They end up turning into hot adults so all transgressions are forgiven (except when he admits he’s only into her cause she’s pretty). But then she’s turned into a swan and he saves her. They live happily ever after.");

        var movie26 = db.getMovieWithTitle("The Swan Princess");
        db.createTag("Animation", movie26.get().getId(), "Tomato", "public");
        db.createTag("Family", movie26.get().getId(), "Tomato", "public");
        db.createTag("Fantasy", movie26.get().getId(), "Tomato", "public");

        db.createRating("Enemies to lovers", "10", "10", "Tomato", movie26.get().getId(), "public");
        db.createRating("Marriage won’t last", "2", "2", "Tomato", movie26.get().getId(), "public");
        db.createRating("She’s in the right", "4", "4", "Tomato", movie26.get().getId(), "public");
    }

    private void createMovie27(DatabaseController db) {
        db.createMovie("Barbie and the Rockers: Out of This World", "Bernard Deyries", "1987", "47min", "Sindy McKay", "Barbie and her friends are in a band and they go to space.");

        var movie27 = db.getMovieWithTitle("Barbie and the Rockers: Out of This World");
        db.createTag("Animation", movie27.get().getId(), "Strawberry", "public");
        db.createTag("Family", movie27.get().getId(), "Strawberry", "public");
        db.createTag("Music", movie27.get().getId(), "Strawberry", "public");

        db.createRating("Knockoff Jem and the holograms", "9", "9", "Strawberry", movie27.get().getId(), "public");
        db.createRating("Barbie movie you never saw", "4", "4", "Strawberry", movie27.get().getId(), "public");
        db.createRating("Forgettable plot", "5", "5", "Strawberry", movie27.get().getId(), "public");
    }

    private void createMovie28(DatabaseController db) {
        db.createMovie("Barbie as Rapunzel", "Owen Hurley", "2002", "1h 24min", "Elana Lesser, Cliff Ruby", "A Rapunzel rendition but with Barbie, and she has a magic brush that lets her leave the castle. She meets the prince and there’s a whole plot twist with two kingdoms, and the evil kidnapper is actually smart.");

        var movie28 = db.getMovieWithTitle("Barbie as Rapunzel");
        db.createTag("Animation", movie28.get().getId(), "Zucchini", "public");
        db.createTag("Family", movie28.get().getId(), "Zucchini", "public");
        db.createTag("Musical", movie28.get().getId(), "Zucchini", "public");

        db.createRating("Creepy looking animals", "10", "10", "Zucchini", movie28.get().getId(), "public");
        db.createRating("Unhelpful animals", "3", "3", "Zucchini", movie28.get().getId(), "public");
        db.createRating("OP protagonist items", "10", "10", "Zucchini", movie28.get().getId(), "public");
    }

    private void createMovie29(DatabaseController db) {
        db.createMovie("Princess Mononoke", "Hayao Miyazaki", "1997", "2h 14min", "Hayao Miyazaki", "Some handsome archer dude defeats demons and meets a beautiful wolf girl, and they fight demons together. And there’s a Jesus-like elk.");

        var movie29 = db.getMovieWithTitle("Princess Mononoke");
        db.createTag("Animation", movie29.get().getId(), "Blueberry", "public");
        db.createTag("Adventure", movie29.get().getId(), "Blueberry", "public");
        db.createTag("Fantasy", movie29.get().getId(), "Blueberry", "public");

        db.createRating("Beautiful animated character", "7", "7", "Blueberry", movie29.get().getId(), "public");
        db.createRating("Handsome animated character", "8", "8", "Blueberry", movie29.get().getId(), "public");
        db.createRating("Best-looking power couple", "9", "9", "Blueberry", movie29.get().getId(), "public");
        db.createRating("Jesus Remake", "3", "3", "Blueberry", movie29.get().getId(), "public");
    }

    private void createMovie30(DatabaseController db) {
        db.createMovie("Barbie: Spy Squad", "Conrad Helten, Ezekiel Norton, Michael Goguen", "2016", "1h 15min", "Marsha Griffin", "Three gymnastics girls become spies, and their arch-nemesis is another gymnastics girl. Basic evil and good organizations movie.");

        var movie30 = db.getMovieWithTitle("Barbie: Spy Squad");
        db.createTag("Animation", movie30.get().getId(), "Rasberry", "public");
        db.createTag("Family", movie30.get().getId(), "Rasberry", "public");

        db.createRating("Basic movies", "4", "4", "Rasberry", movie30.get().getId(), "public");
        db.createRating("Cute side couple", "7", "7", "Rasberry", movie30.get().getId(), "public");
        db.createRating("Not good plot-twists", "2", "2", "Rasberry", movie30.get().getId(), "public");
    }

    private void createMovie31(DatabaseController db) {
        db.createMovie("Barbie in Rock 'N Royals (2015)", "Karen J. Lloyd", "2015", "1h 23min", "Marsha Griffin",
                "Two camps fight against each other in a singing competition, the rockers camp and the princess camp. Main characters are a singer and a princess who accidentally switch places and unite the two camps.");
        var movie31 = db.getMovieWithTitle("Barbie in Rock 'N Royals (2015)");
        db.createTag("Animation", movie31.get().getId(), "Peach", "public");
        db.createTag("Family", movie31.get().getId(), "Peach", "public");

        db.createRating("horrible singing", "10", "10", "Peach", movie31.get().getId(), "public");
        db.createRating("lame dance moves", "6", "6", "Peach", movie31.get().getId(), "public");
        db.createRating("nice hair animation", "8", "8", "Peach", movie31.get().getId(), "public");
    }

    private void createMovie32(DatabaseController db) {
        db.createMovie("Barbie and the Secret Door (2014)", "Karen J. Lloyd", "2014", "1h 21min", "Brian Hohlfeld",
                "Girl with no social life and terrible social skills who is also a princess ends up in a magical world with an overpowered 8-year-old who oppressed people and defeats her with the power of friendship and social skills.");
        var movie32 = db.getMovieWithTitle("Barbie and the Secret Door (2014)");
        db.createTag("Animation", movie32.get().getId(), "JackFruit", "public");
        db.createTag("Family", movie32.get().getId(), "JackFruit", "public");

        db.createRating("bratty kids", "9", "9", "JackFruit", movie32.get().getId(), "public");
        db.createRating("Cute ending", "5", "5", "JackFruit", movie32.get().getId(), "public");
        db.createRating("relatable protagonist", "10", "10", "JackFruit", movie32.get().getId(), "public");
    }

    private void createMovie33(DatabaseController db) {
        db.createMovie("Howl's Moving Castle (2004)", "Hayao Miyazaki", "2004", "1h 59min", "Hayao Miyazaki",
                "A girl who doesn’t know she’s a witch ends up meeting a high-strung wizard dude in a moving castle and helps him out in his daily life. Twists and turns, they’re fated partners and he’s superficial but loves her.");
        var movie33 = db.getMovieWithTitle("Howl's Moving Castle (2004)");
        db.createTag("Animation", movie33.get().getId(), "Lychee", "public");
        db.createTag("Adventure", movie33.get().getId(), "Lychee", "public");
        db.createTag("Fantasy", movie33.get().getId(), "Lychee", "public");

        db.createRating("Dramatic male protagonist", "10", "10", "Lychee", movie33.get().getId(), "public");
        db.createRating("horrible personality male protagonist", "3", "3", "Lychee", movie33.get().getId(), "public");
        db.createRating("unexpected plot twists", "6", "6", "Lychee", movie33.get().getId(), "public");
    }

    private void createMovie34(DatabaseController db) {
        db.createMovie("Ponyo (2008)", "Hayao Miyazaki", "2008", "1h 41min", "Hayao Miyazaki",
                "A tiny child of the sea goddess with red hair and ends washed up on shore because of pollution and gets obsessed with a human 8-year-old boy. The boy ends up feeding her his blood and she falls in love with him, she turns into a toddler fish to chicken to human evolution, the whole world is in shambles, she goes against her father and forces his mom to adopt her or else she dies.");
        var movie34 = db.getMovieWithTitle("Ponyo (2008)");
        db.createTag("Animation", movie34.get().getId(), "Cantaloupe", "public");
        db.createTag("Adventure", movie34.get().getId(), "Cantaloupe", "public");
        db.createTag("Family", movie34.get().getId(), "Cantaloupe", "public");

        db.createRating("Cutest movies", "8", "8", "Cantaloupe", movie34.get().getId(), "public");
        db.createRating("adorable toddler", "6", "6", "Cantaloupe", movie34.get().getId(), "public");
        db.createRating("Beautiful mothers", "10", "10", "Cantaloupe", movie34.get().getId(), "public");
        db.createRating("best-looking animated wives", "6", "6", "Cantaloupe", movie34.get().getId(), "public");
        db.createRating("Annoying child", "10", "10", "Cantaloupe", movie34.get().getId(), "public");
    }

    private void createMovie35(DatabaseController db) {
        db.createMovie("Barbie and the Magic of Pegasus (2005)", "Greg Richardson", "2005", "1h 23min", "Cliff Ruby, Elana Lesser",
                "In this tale, Barbie’s parents lock her in a castle until she’s 16 and she sneaks out all the time because she loves ice skating. Turns out this evil sorcerer dude wants to make her his bride after her sister rejected her 16 years prior, he turns everyone into stone and she flees and meets her sister who was turned into a horse. She then meets this dude who ran away from home because of his gambling issues and they work together to defeat the sorcerer.");
        var movie35 = db.getMovieWithTitle("Barbie and the Magic of Pegasus (2005)");
        db.createTag("Animation", movie35.get().getId(), "Blackberry", "public");
        db.createTag("Family", movie35.get().getId(), "Blackberry", "public");

        db.createRating("Annoying pets", "9", "9", "Blackberry", movie35.get().getId(), "public");
        db.createRating("Useless barbie animals", "10", "10", "Blackberry", movie35.get().getId(), "public");
        db.createRating("Surprisingly sympathetic male lead", "3", "3", "Blackberry", movie35.get().getId(), "public");
        db.createRating("enemies to lovers", "4", "4", "Blackberry", movie35.get().getId(), "public");
    }

    private void createMovie36(DatabaseController db) {
        db.createMovie("Barbie: A Fairy Secret (2011)", "William Lau, Terry Klassen", "2011", "1h 12min", "Elise Allen",
                "A cat fight between Barbie and Raquelle turns into a kidnapping plot with Ken being a forced husband for the queen of the fairy kingdom. Barbie and Raquelle hash it out after finding out they wanted to be friends with each other in the beginning but they were too prideful or shy to admit it.");
        var movie36 = db.getMovieWithTitle("Barbie: A Fairy Secret (2011)");
        db.createTag("Animation", movie36.get().getId(), "Pumpkin", "public");
        db.createTag("Family", movie36.get().getId(), "Pumpkin", "public");

        db.createRating("Cutest romance", "6", "6", "Pumpkin", movie36.get().getId(), "public");
        db.createRating("ridiculous plot", "10", "10", "Pumpkin", movie36.get().getId(), "public");
        db.createRating("funny character", "4", "4", "Pumpkin", movie36.get().getId(), "public");
    }

    private void createMovie37(DatabaseController db) {
        db.createMovie("Barbie: Princess Charm School (2011)", "Ezekiel Norton", "2011", "1h 19min", "Elise Allen, Kati Rocky",
                "Barbie the waitress ends up being picked for princess school against her will after her sister signed her up without her knowledge or consent. She’s adopted and finds out she’s the missing daughter of the late king and queen. Would’ve never gotten the throne if the villain hadn’t admitted to murdering the former King and Queen on live TV.");
        var movie37 = db.getMovieWithTitle("Barbie: Princess Charm School (2011)");
        db.createTag("Animation", movie37.get().getId(), "Butterfly", "public");
        db.createTag("Family", movie37.get().getId(), "Butterfly", "public");

        db.createRating("Cute sisters", "10", "10", "Butterfly", movie37.get().getId(), "public");
        db.createRating("Best Adopted family", "3", "3", "Butterfly", movie37.get().getId(), "public");
        db.createRating("Corny romance", "6", "6", "Butterfly", movie37.get().getId(), "public");
    }

    private void createMovie38(DatabaseController db) {
        db.createMovie("But I'm a Cheerleader (1999)", "Jamie Babbit", "1999", "1h 25min", "Brian Wayne Peterson, Jamie Babbit",
                "This cheerleader thinks she’s straight because she’s a cheerleader but her parents notice she’s not getting laid and think she’s lesbian and send her to gay reformation camp. She still thinks she’s straight (and homophobic) at the beginning and then realizes she is lesbian and she would’ve never realized it if she hadn’t been sent to that camp. She becomes comfortable in her sexuality at the end of the movie.");
        var movie38 = db.getMovieWithTitle("But I'm a Cheerleader (1999)");
        db.createTag("Comedy", movie38.get().getId(), "Coffee", "public");
        db.createTag("Drama", movie38.get().getId(), "Coffee", "public");

        db.createRating("Hilarious", "10", "10", "Coffee", movie38.get().getId(), "public");
        db.createRating("Lipstick lesbian", "2", "2", "Coffee", movie38.get().getId(), "public");
        db.createRating("campy movies", "6", "6", "Coffee", movie38.get().getId(), "public");
    }

    private void createMovie39(DatabaseController db) {
        db.createMovie("Beauty and the Beast (1991)", "Gary Trousdale, Kirk Wise", "1991", "1h 24min", "Linda Woolverton",
                "Girl becomes a prisoner for a beast in place of her father and falls in love with the beast.");
        var movie39 = db.getMovieWithTitle("Beauty and the Beast (1991)");
        db.createTag("Animation", movie39.get().getId(), "Apricotts", "public");
        db.createTag("Family", movie39.get().getId(), "Apricotts", "public");
        db.createTag("Fantasy", movie39.get().getId(), "Apricotts", "public");

        db.createRating("Nice songs", "10", "10", "Apricotts", movie39.get().getId(), "public");
        db.createRating("ugly male protagonist", "4", "4", "Apricotts", movie39.get().getId(), "public");
        db.createRating("Side characters carried the movie", "2", "2", "Apricotts", movie39.get().getId(), "public");
    }

    private void createMovie40(DatabaseController db) {
        db.createMovie("A Silent Voice: The Movie (2016)", "Naoko Yamada", "2016", "2h 10min", "Yoshitoki Ōima (manga), Reiko Yoshida (screenplay)",
                "A deaf child ends up in a class of malicious students and ends up being bullied, the principal ends up getting involved and her mother is upset, her loudest bully gets all the blame (Even though the whole class was in on it) because they don't want to be responsible, he ends up being bullied by the rest of the class because he's a 'bully,' and the loudest bully goes through this whole redemption arch but like no one else in the class gets any flack for what they did to him and her. They never even apologize to either of them, and they pretend to be friends with them in the end. Oh, and both the main leads are suicidal because of the terrible classmates. The movie makes it seem as if they're all supposed to be kumbaya at the end.");

        var movie40 = db.getMovieWithTitle("A Silent Voice: The Movie (2016)");
        db.createTag("Animation", movie40.get().getId(), "Pears", "public");
        db.createTag("Drama", movie40.get().getId(), "Pears", "public");
        db.createTag("Romance", movie40.get().getId(), "Pears", "public");

        db.createRating("Teacher sucks", "10", "10", "Pears", movie40.get().getId(), "public");
        db.createRating("Children to murder", "10", "10", "Pears", movie40.get().getId(), "public");
        db.createRating("Fake people", "10", "10", "Pears", movie40.get().getId(), "public");
        db.createRating("Largest hit list", "10", "10", "Pears", movie40.get().getId(), "public");

        db.createReview(movie40.get().getId(), "I want to murder the blond chick and the teacher and his ex-friend.", "Pears", "public");
        db.createReview(movie40.get().getId(), "They won't last long, life wise.", "Cherry", "public");

        db.createActor("Miyu Irino", "02/19/1988", movie40.get().getId());
        db.createActor("Saori Hayami", "05/29/1991", movie40.get().getId());
        db.createActor("Aoi Yûki", "03/27/1992", movie40.get().getId());
    }

    private void createMovie41(DatabaseController db) {
        db.createMovie("Your Name (2016)", "Makoto Shinkai", "2016", "1h 46min", "Makoto Shinkai",
                "Girl and boy switch bodies and there's a 3-year time difference and she dies if he doesn't save her. He prevents her death and they lose their memories of each other in turn, losing their names. At the end of the movie, they dramatically ask each other's names.");

        var movie41 = db.getMovieWithTitle("Your Name (2016)");
        db.createTag("Animation", movie41.get().getId(), "Avacado", "public");
        db.createTag("Drama", movie41.get().getId(), "Avacado", "public");
        db.createTag("Fantasy", movie41.get().getId(), "Avacado", "public");

        db.createRating("Coolest movie ever", "8", "8", "Avacado", movie41.get().getId(), "public");
        db.createRating("Cute movie", "2", "2", "Avacado", movie41.get().getId(), "public");
        db.createRating("Second watch is better", "4", "4", "Avacado", movie41.get().getId(), "public");

        db.createReview(movie41.get().getId(), "", "Avacado", "public");
        db.createReview(movie41.get().getId(), "", "Pear", "public");
        db.createReview(movie41.get().getId(), "", "Cherry", "public");

        db.createActor("Ryûnosuke Kamiki", "05/19/1989", movie41.get().getId());
        db.createActor("Mone Kamishiraishi", "01/27/1993", movie41.get().getId());
        db.createActor("Ryô Narita", "10/09/1985", movie41.get().getId());
    }

    private void createMovie42(DatabaseController db) {
        db.createMovie("Bolt (2008)", "Byron Howard, Chris Williams", "2008", "1h 36min", "Dan Fogelman, Chris Williams",
                "A dog is a show dog and he thought the stuff in the movies was his real life. He then gets lost and finds out he's not a super dog. He returns to his owner who really does love him.");

        var movie42 = db.getMovieWithTitle("Bolt (2008)");
        db.createTag("Animation", movie42.get().getId(), "Babaco", "public");
        db.createTag("Adventure", movie42.get().getId(), "Babaco", "public");
        db.createTag("Comedy", movie42.get().getId(), "Babaco", "public");

        db.createRating("Animal cuteness", "9", "9", "Babaco", movie42.get().getId(), "public");
        db.createRating("Annoying cat", "7", "7", "Babaco", movie42.get().getId(), "public");
        db.createRating("Owner and pet bond", "5", "5", "Babaco", movie42.get().getId(), "public");

        db.createReview(movie42.get().getId(), "", "Babaco", "public");
        db.createReview(movie42.get().getId(), "", "Apple", "public");
        db.createReview(movie42.get().getId(), "", "Lychee", "public");

        db.createActor("John Travolta", "02/18/1954", movie42.get().getId());
        db.createActor("Miley Cyrus", "11/23/1992", movie42.get().getId());
        db.createActor("Susie Essman", "05/31/1955", movie42.get().getId());
    }

    private void createMovie43(DatabaseController db) {
        db.createMovie("The Secret World of Arrietty (2010)", "Hiromasa Yonebayashi", "2010", "1h 34min", "Mary Norton (novel), Hayao Miyazaki (screenplay)",
                "Cute small humans meet big humans and they survive. Similar plot to that mouse with a needle sword story.");

        var movie43 = db.getMovieWithTitle("The Secret World of Arrietty (2010)");
        db.createTag("Animation", movie43.get().getId(), "Barberries", "public");
        db.createTag("Adventure", movie43.get().getId(), "Barberries", "public");
        db.createTag("Family", movie43.get().getId(), "Barberries", "public");

        db.createRating("Cutest hair accessory", "7", "7", "Barberries", movie43.get().getId(), "public");
        db.createRating("Creative clothes", "9", "9", "Barberries", movie43.get().getId(), "public");
        db.createRating("Appealing height", "3", "3", "Barberries", movie43.get().getId(), "public");

        db.createReview(movie43.get().getId(), "", "Barberries", "public");
        db.createReview(movie43.get().getId(), "", "Banana", "public");
        db.createReview(movie43.get().getId(), "", "Peach", "public");

        db.createActor("Bridgit Mendler", "12/18/1992", movie43.get().getId());
        db.createActor("Amy Poehler", "09/16/1971", movie43.get().getId());
        db.createActor("Will Arnett", "05/04/1970", movie43.get().getId());
    }

    private void createMovie44(DatabaseController db) {
        db.createMovie("Ella Enchanted (2004)", "Tommy O'Haver", "2004", "1h 36min", "Gail Carson Levine (book), Laurie Craig (screenplay)",
                "A girl who is cursed to follow orders ends up falling in love with a prince and goes on a crazy adventure. The book was better. Lots of singing. A musical.");

        var movie44 = db.getMovieWithTitle("Ella Enchanted (2004)");
        db.createTag("Comedy", movie44.get().getId(), "Plum", "public");
        db.createTag("Family", movie44.get().getId(), "Plum", "public");
        db.createTag("Fantasy", movie44.get().getId(), "Plum", "public");

        db.createRating("Good song", "2", "2", "Plum", movie44.get().getId(), "public");
        db.createRating("Weird plot", "6", "6", "Plum", movie44.get().getId(), "public");
        db.createRating("Sassy fairies", "5", "5", "Plum", movie44.get().getId(), "public");

        db.createReview(movie44.get().getId(), "", "Plum", "public");
        db.createReview(movie44.get().getId(), "", "Peach", "public");
        db.createReview(movie44.get().getId(), "", "Strawberry", "public");

        db.createActor("Anne Hathaway", "11/12/1982", movie44.get().getId());
        db.createActor("Hugh Dancy", "06/19/1975", movie44.get().getId());
        db.createActor("Cary Elwes", "10/26/1962", movie44.get().getId());
    }

    private void createMovie45(DatabaseController db) {
        db.createMovie("Sinbad: Legend of the Seven Seas (2003)", "Patrick Gilmore, Tim Johnson", "2003", "1h 26min", "John Logan",
                "Dude steals his best friend's fiance and goes on an adventure with her to save the world. He's cute, so he gets away with it, and the friend is super forgiving.");

        var movie45 = db.getMovieWithTitle("Sinbad: Legend of the Seven Seas (2003)");
        db.createTag("Animation", movie45.get().getId(), "Bearberries", "public");
        db.createTag("Adventure", movie45.get().getId(), "Bearberries", "public");
        db.createTag("Family", movie45.get().getId(), "Bearberries", "public");

        db.createRating("Betrayals", "7", "7", "Bearberries", movie45.get().getId(), "public");
        db.createRating("Would've ended that friendship", "9", "9", "Bearberries", movie45.get().getId(), "public");
        db.createRating("Cute female lead", "4", "4", "Bearberries", movie45.get().getId(), "public");

        db.createReview(movie45.get().getId(), "", "Bearberries", "public");
        db.createReview(movie45.get().getId(), "", "Plum", "public");
        db.createReview(movie45.get().getId(), "", "Peach", "public");

        db.createActor("Brad Pitt", "12/18/1963", movie45.get().getId());
        db.createActor("Catherine Zeta-Jones", "09/25/1969", movie45.get().getId());
        db.createActor("Joseph Fiennes", "05/27/1970", movie45.get().getId());
    }

    private void createMovie46(DatabaseController db) {
        db.createMovie("Coraline (2009)", "Henry Selick", "2009", "1h 40min", "Henry Selick (screenplay), Neil Gaiman (novel)",
                "Not a children's movie, a creepy stop-motion movie about a girl who meets a creepy basement mother who wants to turn her into a doll. Parents are neglectful, so you don't blame even the daughter for trying to leave, and the girl is kind of bratty. Neighbors are creepy.");

        var movie46 = db.getMovieWithTitle("Coraline (2009)");
        db.createTag("Animation", movie46.get().getId(), "Beechnut", "public");
        db.createTag("Drama", movie46.get().getId(), "Beechnut", "public");
        db.createTag("Fantasy", movie46.get().getId(), "Beechnut", "public");

        db.createRating("Creepy neighbors", "10", "10", "Beechnut", movie46.get().getId(), "public");
        db.createRating("Side character came in clutch", "5", "5", "Beechnut", movie46.get().getId(), "public");
        db.createRating("Children's nightmare", "9", "9", "Beechnut", movie46.get().getId(), "public");

        db.createReview(movie46.get().getId(), "", "Beechnut", "public");
        db.createReview(movie46.get().getId(), "", "Bearberries", "public");
        db.createReview(movie46.get().getId(), "", "Barberries", "public");

        db.createActor("Dakota Fanning", "02/23/1994", movie46.get().getId());
        db.createActor("Teri Hatcher", "12/08/1964", movie46.get().getId());
        db.createActor("John Hodgman", "06/03/1973", movie46.get().getId());
    }

    private void createMovie47(DatabaseController db) {
        db.createMovie("The Nightmare Before Christmas (1993)", "Henry Selick", "1993", "1h 16min", "Tim Burton, Michael McDowell, Caroline Thompson",
                "Ridiculous movie about a weird skeleton that wants to be Santa and people indulge in this man's dreams.");

        var movie47 = db.getMovieWithTitle("The Nightmare Before Christmas (1993)");
        db.createTag("Animation", movie47.get().getId(), "Calabash", "public");
        db.createTag("Family", movie47.get().getId(), "Calabash", "public");
        db.createTag("Fantasy", movie47.get().getId(), "Calabash", "public");

        db.createRating("Booty movie", "10", "10", "Calabash", movie47.get().getId(), "public");
        db.createRating("Disgusting movie", "4", "4", "Calabash", movie47.get().getId(), "public");
        db.createRating("Santa's not that great", "8", "8", "Calabash", movie47.get().getId(), "public");

        db.createReview(movie47.get().getId(), "", "Calabash", "public");
        db.createReview(movie47.get().getId(), "", "Beechnut", "public");
        db.createReview(movie47.get().getId(), "", "Cherry", "public");

        db.createActor("Danny Elfman", "05/29/1953", movie47.get().getId());
        db.createActor("Chris Sarandon", "07/24/1942", movie47.get().getId());
        db.createActor("Catherine O'Hara", "03/04/1954", movie47.get().getId());
    }

    private void createMovie48(DatabaseController db) {
        db.createMovie("I Am Legend (2007)", "Francis Lawrence", "2007", "1h 41min", "Mark Protosevich, Akiva Goldsman",
                "A dude survives the apocalypse essentially and ends up saving the world by finding a cure. Took his wife's place.");

        var movie48 = db.getMovieWithTitle("I Am Legend (2007)");
        db.createTag("Drama", movie48.get().getId(), "Carambola", "public");
        db.createTag("Horror", movie48.get().getId(), "Carambola", "public");
        db.createTag("Sci-Fi", movie48.get().getId(), "Carambola", "public");

        db.createRating("Good apocalypse movies", "10", "10", "Carambola", movie48.get().getId(), "public");
        db.createRating("Loyal dog", "2", "2", "Carambola", movie48.get().getId(), "public");
        db.createRating("Nice settings", "3", "3", "Carambola", movie48.get().getId(), "public");

        db.createReview(movie48.get().getId(), "", "Carambola", "public");
        db.createReview(movie48.get().getId(), "", "Cantaloupe", "public");
        db.createReview(movie48.get().getId(), "", "Peach", "public");

        db.createActor("Will Smith", "09/25/1968", movie48.get().getId());
        db.createActor("Alice Braga", "04/15/1983", movie48.get().getId());
        db.createActor("Charlie Tahan", "06/11/1998", movie48.get().getId());
    }

    private void createMovie49(DatabaseController db) {
        db.createMovie("Mirror Mirror (2012)", "Tarsem Singh", "2012", "1h 46min", "Marc Klein, Jason Keller, Melisa Wallack",
                "A Snow White rendition with Snow White fighting bandits and one-upping the evil queen. The prince is essentially useless.");

        var movie49 = db.getMovieWithTitle("Mirror Mirror (2012)");
        db.createTag("Adventure", movie49.get().getId(), "Carob", "public");
        db.createTag("Comedy", movie49.get().getId(), "Carob", "public");
        db.createTag("Drama", movie49.get().getId(), "Carob", "public");

        db.createRating("Sassy evil queen", "10", "10", "Carob", movie49.get().getId(), "public");
        db.createRating("Useless male lead", "6", "6", "Carob", movie49.get().getId(), "public");
        db.createRating("Reused plot", "5", "5", "Carob", movie49.get().getId(), "public");

        db.createReview(movie49.get().getId(), "", "Carob", "public");
        db.createReview(movie49.get().getId(), "", "Cantaloupe", "public");
        db.createReview(movie49.get().getId(), "", "JackFruit", "public");

        db.createActor("Lily Collins", "03/18/1989", movie49.get().getId());
        db.createActor("Julia Roberts", "10/28/1967", movie49.get().getId());
        db.createActor("Armie Hammer", "08/28/1986", movie49.get().getId());
    }

    private void createMovie50(DatabaseController db) {
        db.createMovie("The Secret World of Arrietty (2010)", "Hiromasa Yonebayashi", "2010", "1h 34min", "Mary Norton (novel), Hayao Miyazaki (screenplay)",
                "Cute small humans meet big humans and they survive. Similar plot to that mouse with needle sword story.");

        var movie50 = db.getMovieWithTitle("The Secret World of Arrietty (2010)");
        db.createTag("Animation", movie50.get().getId(), "Quince", "public");
        db.createTag("Adventure", movie50.get().getId(), "Quince", "public");
        db.createTag("Family", movie50.get().getId(), "Quince", "public");

        db.createRating("Cutest hair accessory", "7", "7", "Quince", movie50.get().getId(), "public");
        db.createRating("Creative clothes", "9", "9", "Quince", movie50.get().getId(), "public");
        db.createRating("Appealing height", "3", "3", "Quince", movie50.get().getId(), "public");

        db.createReview(movie50.get().getId(), "", "Quince", "public");
        db.createReview(movie50.get().getId(), "", "JackFruit", "public");
        db.createReview(movie50.get().getId(), "", "Cherry", "public");

        db.createActor("Bridgit Mendler", "12/18/1992", movie50.get().getId());
        db.createActor("Amy Poehler", "09/16/1971", movie50.get().getId());
        db.createActor("Will Arnett", "05/04/1970", movie50.get().getId());
    }

    private void createMovie51(DatabaseController db) {
        db.createMovie("Ramona and Beezus", "Elizabeth Allen Rosenbaum", "2010", "1h 43min", "Laurie Craig (screenplay), Beverly Cleary (book)",
                "A bratty kid ends up doing something important in her insignificant life.");

        var movie51 = db.getMovieWithTitle("Ramona and Beezus");
        db.createTag("Comedy", movie51.get().getId(), "Coconut", "public");
        db.createTag("Drama", movie51.get().getId(), "Coconut", "public");
        db.createTag("Family", movie51.get().getId(), "Coconut", "public");

        db.createRating("Sibling relationship", "10", "10", "Coconut", movie51.get().getId(), "public");
        db.createRating("Selena Gomez movies", "10", "10", "Coconut", movie51.get().getId(), "public");
        db.createRating("Joey King movies", "5", "5", "Coconut", movie51.get().getId(), "public");

        db.createReview(movie51.get().getId(), "", "Coconut", "public");
        db.createReview(movie51.get().getId(), "", "Pear", "public");
        db.createReview(movie51.get().getId(), "", "Blueberry", "public");

        db.createActor("Joey King", "07/30/1999", movie51.get().getId());
        db.createActor("Selena Gomez", "07/22/1992", movie51.get().getId());
        db.createActor("John Corbett", "05/09/1961", movie51.get().getId());
    }

    private void createMovie52(DatabaseController db) {
        db.createMovie("The Lion, the Witch and the Wardrobe", "Andrew Adamson", "2005", "2h 23min", "Ann Peacock (screenplay), Andrew Adamson (screenplay)",
                "A kid walks through a wardrobe and goes into another world. Her family then joins her and they defeat the evil witch. Jesus allegory. This is the second life action made for the book. With the good-looking characters.");

        var movie52 = db.getMovieWithTitle("The Lion, the Witch and the Wardrobe");
        db.createTag("Adventure", movie52.get().getId(), "Clementine", "public");
        db.createTag("Family", movie52.get().getId(), "Clementine", "public");
        db.createTag("Fantasy", movie52.get().getId(), "Clementine", "public");

        db.createRating("Trash brother", "9", "9", "Clementine", movie52.get().getId(), "public");
        db.createRating("Jesus Remake", "3", "3", "Clementine", movie52.get().getId(), "public");
        db.createRating("These kids don't deserve it", "6", "6", "Clementine", movie52.get().getId(), "public");

        db.createReview(movie52.get().getId(), "", "Clementine", "public");
        db.createReview(movie52.get().getId(), "", "Blueberry", "public");
        db.createReview(movie52.get().getId(), "", "Quince", "public");

        db.createActor("Tilda Swinton", "11/05/1960", movie52.get().getId());
        db.createActor("Georgie Henley", "07/09/1995", movie52.get().getId());
        db.createActor("William Moseley", "04/27/1987", movie52.get().getId());
    }

    private void createMovie53(DatabaseController db) {
        db.createMovie("Avatar", "James Cameron", "2009", "2h 42min", "James Cameron",
                "Some dude and his white-passing buddies go to a planet of blue people and want to drain it of its resources. The blue people are suspiciously black people coded, he turns into a blue person and does the dirty with a strong avatar girl which causes him to be anti-colonization.");

        var movie53 = db.getMovieWithTitle("Avatar");
        db.createTag("Action", movie53.get().getId(), "Citron", "public");
        db.createTag("Adventure", movie53.get().getId(), "Citron", "public");
        db.createTag("Science Fiction", movie53.get().getId(), "Citron", "public");

        db.createRating("Sus movie", "9", "9", "Citron", movie53.get().getId(), "public");
        db.createRating("Cool planet", "4", "4", "Citron", movie53.get().getId(), "public");
        db.createRating("Interspecies relationships", "4", "4", "Citron", movie53.get().getId(), "public");
        db.createRating("Aliens", "7", "7", "Citron", movie53.get().getId(), "public");

        db.createReview(movie53.get().getId(), "", "Citron", "public");
        db.createReview(movie53.get().getId(), "", "Clementine", "public");
        db.createReview(movie53.get().getId(), "", "Coconut", "public");

        db.createActor("Sam Worthington", "08/02/1976", movie53.get().getId());
        db.createActor("Zoë Saldana", "06/19/1978", movie53.get().getId());
        db.createActor("Sigourney Weaver", "10/08/1949", movie53.get().getId());
    }

    private void createMovie54(DatabaseController db) {
        db.createMovie("Jurassic Park", "Steven Spielberg", "1993", "2h 7min", "Michael Crichton (novel), Michael Crichton (screenplay)",
                "Some genius decided to bring back dinosaurs through mosquito blood and DNA. Things go wrong.");

        var movie54 = db.getMovieWithTitle("Jurassic Park");
        db.createTag("Adventure", movie54.get().getId(), "Courgette", "public");
        db.createTag("Sci-Fi", movie54.get().getId(), "Courgette", "public");
        db.createTag("Thriller", movie54.get().getId(), "Courgette", "public");

        db.createRating("Predictable", "10", "10", "Courgette", movie54.get().getId(), "public");
        db.createRating("Good idea", "2", "2", "Courgette", movie54.get().getId(), "public");
        db.createRating("Animals", "6", "6", "Courgette", movie54.get().getId(), "public");

        db.createReview(movie54.get().getId(), "", "Courgette", "public");
        db.createReview(movie54.get().getId(), "", "Citron", "public");
        db.createReview(movie54.get().getId(), "", "Apple", "public");

        db.createActor("Sam Neill", "09/14/1947", movie54.get().getId());
        db.createActor("Laura Dern", "02/10/1967", movie54.get().getId());
        db.createActor("Jeff Goldblum", "10/22/1952", movie54.get().getId());
    }

    private void createMovie55(DatabaseController db) {
        db.createMovie("Titanic", "James Cameron", "1997", "3h 14min", "James Cameron",
                "A girl and boy fall in love on a ship and it sinks.");

        var movie55 = db.getMovieWithTitle("Titanic");
        db.createTag("Drama", movie55.get().getId(), "Cranberry", "public");
        db.createTag("Romance", movie55.get().getId(), "Cranberry", "public");

        db.createRating("The plot killed him", "10", "10", "Cranberry", movie55.get().getId(), "public");
        db.createRating("They would've divorced anyways", "5", "5", "Cranberry", movie55.get().getId(), "public");
        db.createRating("Standing with arms out", "9", "9", "Cranberry", movie55.get().getId(), "public");

        db.createReview(movie55.get().getId(), "", "Cranberry", "public");
        db.createReview(movie55.get().getId(), "", "Courgette", "public");
        db.createReview(movie55.get().getId(), "", "Clementine", "public");

        db.createActor("Leonardo DiCaprio", "11/11/1974", movie55.get().getId());
        db.createActor("Kate Winslet", "10/05/1975", movie55.get().getId());
        db.createActor("Billy Zane", "02/24/1966", movie55.get().getId());
    }

    private void createMovie56(DatabaseController db) {
        db.createMovie("Wall-E", "Andrew Stanton", "2008", "1h 38min", "Andrew Stanton (original story), Pete Docter (original story)",
                "A trash picker upper robot meets a fighting robot and falls in love with it. They end up being a better couple than a majority of humanity. Humans are fat and out in space because they destroyed the earth through pollution and an evil sentient robot wants the humans to be ignorant and helpless.");

        var movie56 = db.getMovieWithTitle("Wall-E");
        db.createTag("Animation", movie56.get().getId(), "Cucumber", "public");
        db.createTag("Adventure", movie56.get().getId(), "Cucumber", "public");
        db.createTag("Family", movie56.get().getId(), "Cucumber", "public");

        db.createRating("Unexpected plot twists", "6", "6", "Cucumber", movie56.get().getId(), "public");
        db.createRating("Cutest romance", "6", "6", "Cucumber", movie56.get().getId(), "public");
        db.createRating("Robot love", "4", "4", "Cucumber", movie56.get().getId(), "public");

        db.createReview(movie56.get().getId(), "", "Cucumber", "public");
        db.createReview(movie56.get().getId(), "", "Tomato", "public");
        db.createReview(movie56.get().getId(), "", "Cranberry", "public");

        db.createActor("Ben Burtt", "07/12/1948", movie56.get().getId());
        db.createActor("Elissa Knight", "04/15/1975", movie56.get().getId());
        db.createActor("Jeff Garlin", "06/05/1962", movie56.get().getId());
    }

    private void createMovie57(DatabaseController db) {
        db.createMovie("Wonder Woman", "Patty Jenkins", "2017", "2h 21min", "Allan Heinberg (screenplay), Zack Snyder (story)",
                "An amazonian woman goes outside of her home and becomes a superhero. She also falls in love with this pilot.");

        var movie57 = db.getMovieWithTitle("Wonder Woman");
        db.createTag("Action", movie57.get().getId(), "Durian", "public");
        db.createTag("Adventure", movie57.get().getId(), "Durian", "public");
        db.createTag("Fantasy", movie57.get().getId(), "Durian", "public");

        db.createRating("Corny romance", "6", "6", "Durian", movie57.get().getId(), "public");
        db.createRating("The plot killed him", "10", "10", "Durian", movie57.get().getId(), "public");
        db.createRating("Powerful female lead", "10", "10", "Durian", movie57.get().getId(), "public");

        db.createReview(movie57.get().getId(), "", "Durian", "public");
        db.createReview(movie57.get().getId(), "", "Cucumber", "public");
        db.createReview(movie57.get().getId(), "", "Tomato", "public");

        db.createActor("Gal Gadot", "04/30/1985", movie57.get().getId());
        db.createActor("Chris Pine", "08/26/1980", movie57.get().getId());
        db.createActor("Robin Wright", "04/08/1966", movie57.get().getId());
    }

    private void createMovie58(DatabaseController db) {
        db.createMovie("Little Women", "Greta Gerwig", "2019", "2h 15min", "Greta Gerwig (screenplay), Louisa May Alcott (novel)",
                "Story of sisters and their lives; one of the sisters dies, one marries an ugly old man, one is annoying and spoiled (literally the worst sister) and marries the best dude and ends up living the best life, one marries some poor dude but are in 'love'.");

        var movie58 = db.getMovieWithTitle("Little Women");
        db.createTag("Drama", movie58.get().getId(), "Dates", "public");
        db.createTag("Romance", movie58.get().getId(), "Dates", "public");

        db.createRating("Fatherless troubles", "2", "2", "Dates", movie58.get().getId(), "public");
        db.createRating("Characters done dirty", "8", "8", "Dates", movie58.get().getId(), "public");
        db.createRating("Hateful author", "5", "5", "Dates", movie58.get().getId(), "public");

        db.createReview(movie58.get().getId(), "", "Dates", "public");
        db.createReview(movie58.get().getId(), "", "Durian", "public");
        db.createReview(movie58.get().getId(), "", "Cherry", "public");

        db.createActor("Saoirse Ronan", "04/12/1994", movie58.get().getId());
        db.createActor("Emma Watson", "04/15/1990", movie58.get().getId());
        db.createActor("Florence Pugh", "01/03/1996", movie58.get().getId());
    }

    private void createMovie59(DatabaseController db) {
        db.createMovie("Maleficent", "Robert Stromberg", "2014", "1h 37min", "Linda Woolverton (screenplay), Charles Perrault (based on 'Sleeping Beauty')",
                "Sleeping beauty rendition. Some dude cuts off her wings and now she wants revenge but ends up raising his baby. Tragic.");

        var movie59 = db.getMovieWithTitle("Maleficent");
        db.createTag("Action", movie59.get().getId(), "Dekopon", "public");
        db.createTag("Adventure", movie59.get().getId(), "Dekopon", "public");
        db.createTag("Family", movie59.get().getId(), "Dekopon", "public");

        db.createRating("Cutest movies", "8", "8", "Dekopon", movie59.get().getId(), "public");
        db.createRating("A kid you don't want to eliminate", "5", "5", "Dekopon", movie59.get().getId(), "public");
        db.createRating("Motherly love", "4", "4", "Dekopon", movie59.get().getId(), "public");

        db.createReview(movie59.get().getId(), "", "Dekopon", "public");
        db.createReview(movie59.get().getId(), "", "Banana", "public");
        db.createReview(movie59.get().getId(), "", "Apple", "public");

        db.createActor("Angelina Jolie", "06/04/1975", movie59.get().getId());
        db.createActor("Elle Fanning", "04/09/1998", movie59.get().getId());
        db.createActor("Sharlto Copley", "11/27/1973", movie59.get().getId());
    }

    private void createMovie60(DatabaseController db) {
        db.createMovie("The Emperor's New Groove", "Mark Dindal", "2000", "1h 18min", "Mark Dindal (story), Chris Williams (story)",
                "A spoiled emperor turns into a llama and realizes peasants are humans too.");

        var movie60 = db.getMovieWithTitle("The Emperor's New Groove");
        db.createTag("Animation", movie60.get().getId(), "Fig", "public");
        db.createTag("Adventure", movie60.get().getId(), "Fig", "public");
        db.createTag("Comedy", movie60.get().getId(), "Fig", "public");

        db.createRating("Animal transformation", "10", "10", "Fig", movie60.get().getId(), "public");
        db.createRating("Funny character", "4", "4", "Fig", movie60.get().getId(), "public");
        db.createRating("Brotherly love", "6", "6", "Fig", movie60.get().getId(), "public");

        db.createReview(movie60.get().getId(), "", "Fig", "public");
        db.createReview(movie60.get().getId(), "", "Melon", "public");
        db.createReview(movie60.get().getId(), "", "Cherry", "public");

        db.createActor("Fig Spade", "07/22/1964", movie60.get().getId());
        db.createActor("John Goodman", "06/20/1952", movie60.get().getId());
        db.createActor("Eartha Kitt", "01/17/1927", movie60.get().getId());
    }

    private void createMovie61(DatabaseController db) {
        db.createMovie("Hercules", "Ron Clements, John Musker", "1997", "1h 33min", "Ron Clements (animation story), John Musker (animation story)",
                "Half god man ends up saving the chick and becomes a god but wants to live on earth with his girlfriend. He also does trials and overcomes stuff or something.");

        var movie61 = db.getMovieWithTitle("Hercules");
        db.createTag("Animation", movie61.get().getId(), "Lemon", "public");
        db.createTag("Adventure", movie61.get().getId(), "Lemon", "public");
        db.createTag("Family", movie61.get().getId(), "Lemon", "public");

        db.createRating("Cutest romance", "6", "6", "Lemon", movie61.get().getId(), "public");
        db.createRating("Funny character", "4", "4", "Lemon", movie61.get().getId(), "public");
        db.createRating("Good-looking animated characters", "10", "10", "Lemon", movie61.get().getId(), "public");
        db.createRating("Crush-inducing", "2", "2", "Lemon", movie61.get().getId(), "public");

        db.createReview(movie61.get().getId(), "", "Lemon", "public");
        db.createReview(movie61.get().getId(), "", "Pear", "public");
        db.createReview(movie61.get().getId(), "", "Cranberry", "public");

        db.createActor("Tate Donovan", "09/25/1963", movie61.get().getId());
        db.createActor("Susan Egan", "02/18/1970", movie61.get().getId());
        db.createActor("James Woods", "04/18/1947", movie61.get().getId());
    }

    private void createMovie62(DatabaseController db) {
        db.createMovie("Weathering with You", "Makoto Shinkai", "2019", "1h 52min", "Makoto Shinkai",
                "Some dude meets this girl after running away from home that can control the weather. Stuff happens and they live happily as a broke ordinary couple.");

        var movie62 = db.getMovieWithTitle("Weathering with You");
        db.createTag("Animation", movie62.get().getId(), "Dewberries", "public");
        db.createTag("Drama", movie62.get().getId(), "Dewberries", "public");
        db.createTag("Fantasy", movie62.get().getId(), "Dewberries", "public");

        db.createRating("Ok romance", "10", "10", "Dewberries", movie62.get().getId(), "public");
        db.createRating("Not worth it", "7", "7", "Dewberries", movie62.get().getId(), "public");
        db.createRating("They will divorce from lack of planning", "4", "4", "Dewberries", movie62.get().getId(), "public");

        db.createReview(movie62.get().getId(), "", "Dewberries", "public");
        db.createReview(movie62.get().getId(), "", "Cherry", "public");
        db.createReview(movie62.get().getId(), "", "Cantaloupe", "public");

        db.createActor("Kotaro Daigo", "04/01/2002", movie62.get().getId());
        db.createActor("Nana Mori", "11/21/2000", movie62.get().getId());
        db.createActor("Shun Oguri", "12/26/1982", movie62.get().getId());
    }

    private void createMovie63(DatabaseController db) {
        db.createMovie("Whisper of the Heart", "Yoshifumi Kondô", "1995", "1h 51min", "Hayao Miyazaki (screenplay), Aoi Hiiragi (comic)",
                "A film about a girl who wants to be a writer and a boy who pursues his dreams to be a piano maker. A romance between them changes their lives forever.");

        var movie63 = db.getMovieWithTitle("Whisper of the Heart");
        db.createTag("Animation", movie63.get().getId(), "Dracontomelon", "public");
        db.createTag("Drama", movie63.get().getId(), "Dracontomelon", "public");
        db.createTag("Family", movie63.get().getId(), "Dracontomelon", "public");

        db.createRating("They will divorce from lack of planning", "4", "4", "Dracontomelon", movie63.get().getId(), "public");
        db.createRating("Cutest romance", "6", "6", "Dracontomelon", movie63.get().getId(), "public");
        db.createRating("Nice songs", "10", "10", "Dracontomelon", movie63.get().getId(), "public");

        db.createReview(movie63.get().getId(), "", "Dracontomelon", "public");
        db.createReview(movie63.get().getId(), "", "Dewberries", "public");
        db.createReview(movie63.get().getId(), "", "Lemon", "public");

        db.createActor("Youko Honna", "09/22/1976", movie63.get().getId());
        db.createActor("Issei Takahashi", "12/17/1959", movie63.get().getId());
        db.createActor("Takashi Tachibana", "01/17/1957", movie63.get().getId());
    }

    private void createMovie64(DatabaseController db) {
        db.createMovie("Kiki's Delivery Service", "Hayao Miyazaki", "1989", "1h 43min", "Eiko Kadono (novel), Hayao Miyazaki (screenplay)",
                "A witch girl moves to the city to be independent from her parents. Cute film with romance and humor.");

        var movie64 = db.getMovieWithTitle("Kiki's Delivery Service");
        db.createTag("Animation", movie64.get().getId(), "Dragonfruit", "public");
        db.createTag("Adventure", movie64.get().getId(), "Dragonfruit", "public");
        db.createTag("Family", movie64.get().getId(), "Dragonfruit", "public");

        db.createRating("Cutest romance", "6", "6", "Dragonfruit", movie64.get().getId(), "public");
        db.createRating("Funny character", "4", "4", "Dragonfruit", movie64.get().getId(), "public");
        db.createRating("Chill movie", "5", "5", "Dragonfruit", movie64.get().getId(), "public");

        db.createReview(movie64.get().getId(), "", "Dragonfruit", "public");
        db.createReview(movie64.get().getId(), "", "Binura", "public");
        db.createReview(movie64.get().getId(), "", "Banana", "public");

        db.createActor("Kirsten Dunst", "04/30/1982", movie64.get().getId());
        db.createActor("Minami Takayama", "05/05/1964", movie64.get().getId());
        db.createActor("Rei Sakuma", "01/05/1965", movie64.get().getId());
    }

    private void createMovie65(DatabaseController db) {
        db.createMovie("My Neighbor Totoro", "Hayao Miyazaki", "1988", "1h 26min", "Hayao Miyazaki",
                "This girl and her sister move to the countryside and meet this huge spirit rabbit that they go on adventures with.");

        var movie65 = db.getMovieWithTitle("My Neighbor Totoro");
        db.createTag("Animation", movie65.get().getId(), "Eggplant", "public");
        db.createTag("Family", movie65.get().getId(), "Eggplant", "public");
        db.createTag("Fantasy", movie65.get().getId(), "Eggplant", "public");

        db.createRating("Cutest movies", "8", "8", "Eggplant", movie65.get().getId(), "public");
        db.createRating("Weird movie", "3", "3", "Eggplant", movie65.get().getId(), "public");
        db.createRating("Animal cuteness", "9", "9", "Eggplant", movie65.get().getId(), "public");

        db.createReview(movie65.get().getId(), "", "Eggplant", "public");
        db.createReview(movie65.get().getId(), "", "Dragonfruit", "public");
        db.createReview(movie65.get().getId(), "", "Binura", "public");

        db.createActor("Hitoshi Takagi", "10/17/1959", movie65.get().getId());
        db.createActor("Noriko Hidaka", "05/31/1962", movie65.get().getId());
        db.createActor("Chika Sakamoto", "08/17/1957", movie65.get().getId());
    }

    private void createMovie66(DatabaseController db) {
        db.createMovie("The Red Turtle", "Michael Dudok de Wit", "2016", "1h 20min", "Michael Dudok de Wit (screenplay), Pascale Ferran (dialogue)",
                "Some dude gets stranded on an island and captures a turtle when he goes fishing. The turtle turns into a woman before he can eat it and he (Consent is ambiguous) she stays with him and she ends up having a child. At the end the turtle girl wants to go to the sea and she dies and the turtle/human child leaves the island. Whether he leaves for the human world or just the sea itself is also ambiguous.");

        var movie66 = db.getMovieWithTitle("The Red Turtle");
        db.createTag("Animation", movie66.get().getId(), "Huckleberry", "public");
        db.createTag("Fantasy", movie66.get().getId(), "Huckleberry", "public");

        db.createRating("Weird plot", "6", "6", "Huckleberry", movie66.get().getId(), "public");
        db.createRating("Strange movie", "8", "8", "Huckleberry", movie66.get().getId(), "public");
        db.createRating("Not sure how to feel", "10", "10", "Huckleberry", movie66.get().getId(), "public");

        db.createReview(movie66.get().getId(), "", "Huckleberry", "public");
        db.createReview(movie66.get().getId(), "", "Cherry", "public");
        db.createReview(movie66.get().getId(), "", "Banana", "public");

        db.createActor("Emmanuel Garijo", "03/18/1960", movie66.get().getId());
        db.createActor("Tom Hudson", "11/14/1984", movie66.get().getId());
        db.createActor("Baptiste Goy", "12/05/1972", movie66.get().getId());
    }

    private void createMovie67(DatabaseController db) {
        db.createMovie("The Cat Returns", "Hiroyuki Morita", "2002", "1h 15min", "Aoi Hiiragi (comic), Reiko Yoshida (screenplay)",
                "A girl with a cat obsession goes into a world where cats live freely and talk. She falls in love with the cat baron and slowly turns into a cat but decides she wants to stay human. She keeps a little cat baron statue with her.");

        var movie67 = db.getMovieWithTitle("The Cat Returns");
        db.createTag("Animation", movie67.get().getId(), "Etrog", "public");
        db.createTag("Adventure", movie67.get().getId(), "Etrog", "public");
        db.createTag("Comedy", movie67.get().getId(), "Etrog", "public");

        db.createRating("Cutest movies", "8", "8", "Etrog", movie67.get().getId(), "public");
        db.createRating("Talking animals", "2", "2", "Etrog", movie67.get().getId(), "public");
        db.createRating("Interspecies relationships", "4", "4", "Etrog", movie67.get().getId(), "public");

        db.createReview(movie67.get().getId(), "", "Etrog", "public");
        db.createReview(movie67.get().getId(), "", "Cranberry", "public");
        db.createReview(movie67.get().getId(), "", "Fig", "public");

        db.createActor("Chizuru Ikewaki", "11/17/1981", movie67.get().getId());
        db.createActor("Yoshihiko Hakamada", "12/23/1982", movie67.get().getId());
        db.createActor("Aki Maeda", "07/11/1985", movie67.get().getId());
    }

    private void createMovie68(DatabaseController db) {
        db.createMovie("A Whisker Away", "Jun'ichi Satô, Tomotaka Shibayama", "2020", "1h 44min", "Mari Okada",
                "A girl turns into a cat after school and ends up falling in love with a boy who takes her in. She tries to flirt with him as a human and fails. So she contemplates on whether she should stay a cat forever.");

        var movie68 = db.getMovieWithTitle("A Whisker Away");
        db.createTag("Animation", movie68.get().getId(), "Elderberry", "public");
        db.createTag("Drama", movie68.get().getId(), "Elderberry", "public");
        db.createTag("Fantasy", movie68.get().getId(), "Elderberry", "public");

        db.createRating("Weird plot", "6", "6", "Elderberry", movie68.get().getId(), "public");
        db.createRating("Animal cuteness", "9", "9", "Elderberry", movie68.get().getId(), "public");
        db.createRating("They will divorce from lack of planning", "4", "4", "Elderberry", movie68.get().getId(), "public");

        db.createReview(movie68.get().getId(), "", "Elderberry", "public");
        db.createReview(movie68.get().getId(), "", "Cherry", "public");
        db.createReview(movie68.get().getId(), "", "Cranberry", "public");

        db.createActor("Mirai Shida", "05/10/1993", movie68.get().getId());
        db.createActor("Natsuki Hanae", "06/26/1991", movie68.get().getId());
        db.createActor("Susumu Chiba", "08/13/1970", movie68.get().getId());
    }

    private void createMovie69(DatabaseController db) {
        db.createMovie("Princess Principal: Crown Handler", "Masaki Tachibana", "2021", "1h 57min",
                "Ichirô Ôkouchi (screenplay), Masaki Tachibana (screenplay)",
                "A bunch of elite female spies go on a mission and stuff happens. Super nice animated movie.");

        var movie69 = db.getMovieWithTitle("Princess Principal: Crown Handler");
        db.createTag("Animation", movie69.get().getId(), "Emblica", "public");
        db.createTag("Action", movie69.get().getId(), "Emblica", "public");
        db.createTag("Adventure", movie69.get().getId(), "Emblica", "public");

        db.createRating("Cute female lead", "4", "4", "Emblica", movie69.get().getId(), "public");
        db.createRating("Powerful female lead", "10", "10", "Emblica", movie69.get().getId(), "public");
        db.createRating("Girl power", "7", "7", "Emblica", movie69.get().getId(), "public");

        db.createReview(movie69.get().getId(), "", "Emblica", "public");
        db.createReview(movie69.get().getId(), "", "Elderberry", "public");
        db.createReview(movie69.get().getId(), "", "Lemon", "public");

        db.createActor("Beverly Caimen", "03/25/1996", movie69.get().getId());
        db.createActor("Aoife O'Donnell", "09/18/1990", movie69.get().getId());
        db.createActor("Ray Chase", "05/20/1987", movie69.get().getId());
    }

    private void createMovie70(DatabaseController db) {
        db.createMovie("Dragon Ball Super: Broly", "Tatsuya Nagamine", "2018", "1h 41min",
                "Akira Toriyama (character 'Vegeta' and 'Frieza'), Akira Toriyama (screenplay)",
                "A boy gets abused by his father and fights other dudes and meets a hot alien who understands him. Goku wants to fight him regardless of this man's mental health issues.");

        var movie70 = db.getMovieWithTitle("Dragon Ball Super: Broly");
        db.createTag("Animation", movie70.get().getId(), "Goumi", "public");
        db.createTag("Action", movie70.get().getId(), "Goumi", "public");
        db.createTag("Fantasy", movie70.get().getId(), "Goumi", "public");

        db.createRating("Interspecies relationships", "4", "4", "Goumi", movie70.get().getId(), "public");
        db.createRating("Strong male lead", "9", "9", "Goumi", movie70.get().getId(), "public");
        db.createRating("Father needs to be gone", "10", "10", "Goumi", movie70.get().getId(), "public");

        db.createReview(movie70.get().getId(), "", "Goumi", "public");
        db.createReview(movie70.get().getId(), "", "Emblica", "public");
        db.createReview(movie70.get().getId(), "", "Elderberry", "public");

        db.createActor("Masako Nozawa", "10/25/1936", movie70.get().getId());
        db.createActor("Aya Hisakawa", "11/12/1968", movie70.get().getId());
        db.createActor("Ryô Horikawa", "02/01/1958", movie70.get().getId());
    }
}

