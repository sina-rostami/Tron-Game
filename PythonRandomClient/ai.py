# -*- coding: utf-8 -*-

# python imports
import random

# chillin imports
from chillin_client import RealtimeAI

# project imports
from ks.models import ECell, EDirection, Position
from ks.commands import ChangeDirection, ActivateWallBreaker


class AI(RealtimeAI):
    dir = EDirection.Up
    def __init__(self, world):
        super(AI, self).__init__(world)


    def initialize(self):
        print('initialize')


    def decide(self):
        print('decide')

        # self.send_command(ChangeDirection(random.choice(list(EDirection))))
        self.send_command(ChangeDirection(dir))
        
        dir = EDirection.Left;
        if self.world.agents[self.my_side].wall_breaker_cooldown == 0:
            self.send_command(ActivateWallBreaker())
