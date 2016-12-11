package se.callista.cardgame.demo

case class Card(number: Integer, color: String) {
  def canStackCard(other: Card): Boolean = number == other.number || color == other.color
}

case class Deck(cards: List[Card]) {
  def getTopCard: Card = cards.head

  def canStack(other: Deck): Boolean = this.getTopCard.canStackCard(other.getTopCard)
}

object Deck {
  def apply(card: Card): Deck = Deck(List(card))

  def newShuffled: Deck = {
    import scala.util.Random
    val cards = for (i <- 0 until 13; c <- List("SPADES", "HEARTS", "CLUBS", "DIAMONDS")) yield Card(i, c)

    Deck(Random.shuffle(cards).to[List])
  }
}

class Game {

  import scala.annotation.tailrec

  @tailrec
  private def start(tries: Int = 0): Unit =
    round(Deck.newShuffled) match {
      case _ :: Nil => println("Succeeded after " + tries + " tries!")
      case _ => start(tries + 1)
    }

  private def round(deck: Deck): List[Deck] = {
    @tailrec def loop(deck: Deck, stacks: List[Deck] = List()): List[Deck] = deck match {
      case Deck(topCard :: rest) =>
        loop(Deck(rest), tryStack(Deck(topCard) :: stacks))
      case _ => stacks
    }

    loop(deck)
  }

  private def tryStack(stacks: List[Deck]): List[Deck] = {
    def stack(d1: Deck, d2: Deck): Deck = Deck(d1.cards ++ d2.cards)

    def update(stack: List[Deck], pos: Int, deck: Deck) = stack.tail.updated(pos, deck)

    stacks match {
      case first :: adjacent :: _ if first.canStack(adjacent) => tryStack(update(stacks, pos = 0, stack(first, adjacent)))
      case first :: _ :: _ :: threeSlotsAway :: _ if first.canStack(threeSlotsAway) => tryStack(update(stacks, pos = 2, stack(first, threeSlotsAway)))
      case first :: rest => first :: tryStack(rest)
      case _ => stacks
    }
  }
}

object Game {
  def main(args: Array[String]): Unit = new Game().start()
}